select setval('users_seq', 9, true); -- next val is 10

INSERT INTO public.users (id, email, password, role, username)
    VALUES (nextval('users_seq'), 'jane@example.com', 'hashedPassword:abc', 'USER', 'jane');
