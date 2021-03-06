--postgres creates indexes on unique columns by default, so separate index not required
CREATE TABLE user_details(
	id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	password VARCHAR (50),
	email VARCHAR (100) UNIQUE NOT NULL,
	phone VARCHAR (10) UNIQUE NOT NULL,
	meta VARCHAR (500)
);
