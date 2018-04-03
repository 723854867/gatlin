local flag=redis.call("MSETNX", unpack(ARGV, 2))
if (flag == 1)
then
	local len = #(ARGV) - 1
	for index=2, len, 2
	do
		redis.call("PEXPIRE", ARGV[index], ARGV[1])	
	end
end
return flag