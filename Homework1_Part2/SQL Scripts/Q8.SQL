select a.email from user_table a where a.user_id in (
select a.author_id from tweets a, tweets b
where a.tweet_id = b.parent_tweet_id
and b.tweet_id =128953 
)