select  distinct a.user_id, a.nickname, a.email from user_table a , tweets b
where a.user_id = b.author_id 
and b.tweet_sent_time between to_timestamp( '2012-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss' ) and to_timestamp('2013-02-11 23:59:59', 'yyyy-mm-dd hh24:mi:ss')
order by a.user_id