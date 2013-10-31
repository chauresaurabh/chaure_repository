SELECT a.FIRSTNAME ||' '|| a.LASTNAME as FULLNAME FROM user_table a, user_table b, FOLLOWS c
where a.user_id = c.user_id
and c.follows_id = b.user_id
and b.nickname = 'mickey'
order by a.user_id