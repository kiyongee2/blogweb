-- 같은 제목의 글이 없을 때만 넣는다 → 서버를 여러 번 재시작해도 3개만 유지
-- PostgreSQL은 FROM 없이 SELECT 가능 (MySQL의 FROM DUAL 불필요)
INSERT INTO article (title, content, image_url, created_at, updated_at)
SELECT '제목 1 입니다.', '내용 1 입니다.', NULL, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM article WHERE title = '제목 1 입니다.');

INSERT INTO article (title, content, image_url, created_at, updated_at)
SELECT '제목 2 입니다.', '내용 2 입니다.', NULL, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM article WHERE title = '제목 2 입니다.');

INSERT INTO article (title, content, image_url, created_at, updated_at)
SELECT '제목 3 입니다.', '내용 3 입니다.', NULL, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM article WHERE title = '제목 3 입니다.');
