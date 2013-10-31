SELECT avg(trunc((sysdate-a.date_of_birth)/365)) as AVGERAGE_AGE FROM user_table a, user_table b, FOLLOWS c
where a.user_id = c.user_id
and c.follows_id = b.user_id
and b.FIRSTNAME ||' '|| b.LASTNAME ='Lady Gaga'