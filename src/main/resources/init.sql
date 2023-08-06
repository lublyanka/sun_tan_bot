CREATE DATABASE suntan WITH owner = postgres;

create table if not exists suntan.public.levels
(
    id         integer not null
        primary key,
    backm      double precision,
    frontm     double precision,
    leftsidem  double precision,
    rightsidem double precision,
    shadem     bigint
);

alter table suntan.public.levels
    owner to postgres;

INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (1, 1.5, 1.5, 1, 1, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (2, 2, 2, 1, 1, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (3, 3, 3, 1.5, 1.5, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (4, 5, 5, 2.5, 2.5, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (5, 7, 7, 3, 3, 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (6, 9, 9, 5, 5, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (7, 12, 12, 7, 7, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (8, 15, 15, 10, 10, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (9, 20, 20, 15, 15, 15) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (10, 25, 25, 20, 20, 20) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (11, 35, 35, 25, 25, 30) ON CONFLICT (id) DO NOTHING;
INSERT INTO suntan.public.levels (id, backm, frontm, leftsidem, rightsidem, shadem) VALUES (12, 45, 45, 30, 30, 40) ON CONFLICT (id) DO NOTHING;