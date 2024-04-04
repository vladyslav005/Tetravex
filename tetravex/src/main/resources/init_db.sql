DROP TABLE IF EXISTS Score;
DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS Comment;

CREATE TABLE Score
(
    id       int PRIMARY KEY,
    player   VARCHAR(64),
    game     VARCHAR(64),
    points   INTEGER,
    playedOn TIMESTAMP
);

CREATE TABLE Rating
(
    id      int PRIMARY KEY,
    player  VARCHAR(64),
    game    VARCHAR(64),
    rating  INTEGER,
    ratedOn TIMESTAMP,
    PRIMARY KEY (player, game)
);

CREATE TABLE Comment
(
    id          int PRIMARY KEY,
    player      VARCHAR(64),
    game        VARCHAR(64),
    comment     VARCHAR(64),
    commentedOn TIMESTAMP
);