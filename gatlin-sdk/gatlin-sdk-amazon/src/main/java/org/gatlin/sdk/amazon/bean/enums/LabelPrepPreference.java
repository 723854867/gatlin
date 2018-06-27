package org.gatlin.sdk.amazon.bean.enums;

public enum LabelPrepPreference {

	// 如需要标签，则卖家将为入库货件的商品贴标。
	SELLER_LABEL,
	// 如需要标签，则亚马逊将尝试为入库货件的商品贴标。如果亚马逊确定其不具备为商品成功贴标所需的信息，则入库货件计划中不包括该商品。
	AMAZON_LABEL_ONLY,
	// 如需要标签，则亚马逊将尝试为入库货件的商品贴标。如果亚马逊确定其不具备为商品成功贴标所需的信息，则入库货件计划中包括该商品且卖家必须为其贴标。
	AMAZON_LABEL_PREFERRED,
}
