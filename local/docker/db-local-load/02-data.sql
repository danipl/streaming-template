#User
insert into st.user (name, age, password, create_at) values ('test_1', 30, MD5('test'), now());
insert into st.user (name, age, password, create_at) values ('test_2', 20, MD5('test'), now());
insert into st.user (name, age, password, create_at) values ('test_3', 21, MD5('test'), now());
insert into st.user (name, age, password, create_at) values ('test_4', 36, MD5('test'), now());
insert into st.user (name, age, password, create_at) values ('test_5', 10, MD5('test'), now());

#Admin
insert into st.admin (name, password) values ('admin_1', MD5('admin'));