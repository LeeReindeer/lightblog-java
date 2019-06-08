# Redis data structure design

Use Redis to cache the "vote" count, if the cache not exists, we get data from MySQL. 
The cache will expire in one hour, and we also flush the data to MySQL in one hour period. 

## set

- like:blog_id
  `SADD like:1 1` // user id 1 like blog id 1

- unlike:blog_id

## string

- cnt:like:1
  `SET cnt:like:1 1`
  `INCR cnt:like:1`

