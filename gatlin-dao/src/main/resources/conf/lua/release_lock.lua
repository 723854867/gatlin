local count=0
for i=1, #(KEYS), 1
do
	if redis.call("get", KEYS[i]) == ARGV[1]
	then
		local deleted=redis.call("del", KEYS[i])
		count=count + deleted
	end
end
return count