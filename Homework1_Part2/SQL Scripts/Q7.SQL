select a.user_id, a.FIRSTNAME ||' '|| a.LASTNAME as FULLNAME from user_table a  where a.user_id not in ( select author_id  from tweets ) order by a.user_id
