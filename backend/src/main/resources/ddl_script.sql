
drop table if exists canvas;

CREATE TABLE canvas (
    uuid VARCHAR(100) PRIMARY KEY,
    area VARCHAR(20) NOT NULL,  -- Stores "x1,y1,x2,y2" as a string
    created_on VARCHAR(30) NOT NULL,
    days INT NOT NULL CHECK (days BETWEEN 1 AND 30),
    url VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    hover_text VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Index for faster lookups on active ads
CREATE INDEX idx_canvas_active ON canvas (active);

commit;

select * from canvas;


