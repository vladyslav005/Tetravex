CREATE TABLE Score (
    player VARCHAR(64),
    game VARCHAR(64),
    points INTEGER,
    playedOn TIMESTAMP
);

CREATE TABLE Rating (
    player VARCHAR(64),
    game  VARCHAR(64),
    rating  INTEGER,
    ratedOn TIMESTAMP,
    PRIMARY KEY (player, game)
);

CREATE TABLE Comment (
     player VARCHAR(64),
     game VARCHAR(64),
     comment VARCHAR(64),
     commentedOn TIMESTAMP
);