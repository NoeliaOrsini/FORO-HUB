ALTER TABLE topicos
MODIFY mensaje TEXT,
MODIFY titulo VARCHAR(250),
MODIFY nombre_curso VARCHAR(250),
MODIFY status VARCHAR(100) DEFAULT 'ABIERTO',  -- Establecer valor por defecto como 'ABIERTO'
MODIFY autor VARCHAR(150);