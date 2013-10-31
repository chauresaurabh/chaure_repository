select a.nickname from user_table a 
where a.user_id in (
  select sender_id from private_message a, user_table b
  where a.receiver_id = b.user_id and b.nickname='mickey'
  group by ( sender_id ) having count(sender_id) > 2
)