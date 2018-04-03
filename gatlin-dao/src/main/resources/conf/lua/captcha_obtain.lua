local val = redis.call("pttl", KEYS[1])
if ((val ~= -1) and (val ~= -2))
then
	local interval = tonumber(ARGV[2]) - val
	if (interval < tonumber(ARGV[5]))
	then
		return 1
	end
end
local codeCount = redis.call("get", KEYS[2])
if (codeCount and tonumber(codeCount) >= tonumber(ARGV[3]))
then
	return 2
else
	redis.call("set", KEYS[1], ARGV[1], "PX", ARGV[2])
	redis.call("incr", KEYS[2])
	redis.call("pexpire", KEYS[2], ARGV[4])
	return 0
end
