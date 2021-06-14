1. order by 정렬을 이용
  SELECT
      DATETIME
  FROM
      ANIMAL_INS
  order by DATETIME
  limit 1


2. min함수를 이용
  SELECT 
      MIN(DATETIME) AS DATETIME
  FROM ANIMAL_INS
