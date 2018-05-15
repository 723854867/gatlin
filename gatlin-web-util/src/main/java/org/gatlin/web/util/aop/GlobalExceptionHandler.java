package org.gatlin.web.util.aop;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.bean.exceptions.GatlinRuntimeException;
import org.gatlin.core.bean.exceptions.SdkException;
import org.gatlin.core.bean.model.code.Code;
import org.gatlin.core.bean.model.message.Response;
import org.gatlin.web.util.WebCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Response<Void> handler(Exception e) {
		Response<Void> response = Response.error(CoreCode.SYSTEM_ERR);
		if (e instanceof IllegalArgumentException) {
			logger.warn("Controller 方法参数绑定失败，请注意是否使用 @RequestParam！", e);
			return response;
		} else if (e instanceof DuplicateKeyException) {
			logger.info("主键异常冲突：", e.getCause());
			return Response.error(CoreCode.SYSTEM_ERR);
		} else {
			response.appendDesc(e.getMessage());
			logger.warn("系统错误！", e);
			return response;
		}
	}

	@ResponseBody
	@ExceptionHandler({ GatlinRuntimeException.class })
	public Response<Void> bizExceptionHandler(Exception e) {
		if (e instanceof CodeException) {
			CodeException exception = (CodeException) e;
			Code code = exception.code();
			if (code.key().equals(CoreCode.SYSTEM_ERR.key()))
				logger.error("系统错误！", e);
			return Response.error(code);
		}
		if (e instanceof SdkException) {
			Response<Void> response = Response.error(CoreCode.SDK_INVOKE_FAIL);
			response.appendDesc(((SdkException) e).desc());
			return response;
		}
		return Response.error(CoreCode.SYSTEM_ERR);
	}

	@ResponseBody
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response<Void> httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
		return Response.error(WebCode.UNSUPPORT_HTTP_METHOD);
	}

	@ResponseBody
	@ExceptionHandler({ HttpMessageNotReadableException.class, MissingServletRequestParameterException.class })
	public Response<Void> httpMessageNotReadableException(Exception ex) {
		Response<Void> response = Response.error(CoreCode.PARAM_ERR);
		response.appendDesc(ex.toString());
		return response;
	}

	@ResponseBody
	@ExceptionHandler(BindException.class)
	public Response<Void> bindExceptionHandler(BindException ex) {
		return _validatorError(ex.getBindingResult());
	}

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public Response<Void> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		Response<Void> response = Response.error(CoreCode.PARAM_ERR);
		Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
		StringBuilder reason = new StringBuilder("[");
		for (ConstraintViolation<?> constraintViolation : set)
			reason.append(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName()).append("-")
					.append(constraintViolation.getMessage()).append(";");
		reason.deleteCharAt(reason.length() - 1);
		reason.append("]");
		response.appendDesc(reason.toString());
		return response;
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		return _validatorError(ex.getBindingResult());
	}

	/**
	 * 上传文件超过上限
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Response<Void> uploadSizeExceededHandler(MaxUploadSizeExceededException ex) {
		Response<Void> response = Response.error(WebCode.UPLOAD_SIZE_EXCEEDED);
		response.appendDesc(String.valueOf(ex.getMaxUploadSize()));
		return response;
	}

	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Response<Void> unsupportedMediaTypeHandler(HttpMediaTypeNotSupportedException ex) {
		Response<Void> response = Response.error(WebCode.UNSUPPORT_CONTENT_TYPE);
		response.appendDesc(ex.getContentType().toString());
		return response;
	} 

	private Response<Void> _validatorError(BindingResult bindingResult) {
		List<FieldError> errors = bindingResult.getFieldErrors();
		StringBuilder reason = new StringBuilder("[");
		for (FieldError error : errors)
			reason.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
		reason.deleteCharAt(reason.length() - 1);
		reason.append("]");
		Response<Void> response = Response.error(CoreCode.PARAM_ERR);
		response.appendDesc(reason.toString());
		return response;
	}
}
