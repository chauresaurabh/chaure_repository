select r.nickname as user1_nickname, r.location as user1_location, r.email as user1_email,
t.nickname as user2_nickname, t.location as user2_location, t.email as user2_email from user_table r , user_table t, ( 
            select a.user_id, a.follows_id from follows a,
           follows b where a.user_id = b.follows_id and b.user_id = a.follows_id  AND a.user_id < b.user_id
           order by  a.user_id
  ) d 
where r.user_id=d.user_id
and t.user_id = d.follows_id 