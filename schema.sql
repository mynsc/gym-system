CREATE TABLE persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nombre_de_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasenia VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE estudiante (
    id_persona INT PRIMARY KEY,
    tipo_de_estudiante VARCHAR(30),
    facultad VARCHAR(100),
    carrera VARCHAR(100),
    base_inicio VARCHAR(10),
    autoseguro_activo BOOLEAN DEFAULT FALSE,
    matriculado_semestre_actual BOOLEAN DEFAULT FALSE,
    presenta_lesion BOOLEAN DEFAULT FALSE,
    deporte VARCHAR(50),
    tipo_de_discapacidad VARCHAR(50),
    nivel_de_discapacidad VARCHAR(50),
    ultima_visita DATE,
    visitante_concurrente BOOLEAN DEFAULT FALSE,
    presenta_reservacion BOOLEAN DEFAULT FALSE,
    presenta_penalidades BOOLEAN DEFAULT FALSE,
    cantidad_penalidades SMALLINT DEFAULT 0,
    vetado_temporalmente BOOLEAN DEFAULT FALSE,
    numero_de_puntos INT DEFAULT 0,
    nivel INT DEFAULT 0,
    FOREIGN KEY (id_persona) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE rutina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    objetivo TEXT,
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(id_persona) ON DELETE CASCADE
);

CREATE TABLE ejercicio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_rutina INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    series INT NOT NULL,
    repeticiones INT NOT NULL,
    FOREIGN KEY (id_rutina) REFERENCES rutina(id) ON DELETE CASCADE
);

-- Ejemplo de lo que harás después, no lo corras todavía
ALTER TABLE estudiante 
ADD COLUMN id_reserva INT,
ADD CONSTRAINT fk_estudiante_reserva FOREIGN KEY (id_reserva) REFERENCES reserva(id);

INSERT INTO persona(
    nombre,
    apellido,
    nombre_de_usuario,
    contrasenia,
    rol
) VALUES ('Juan', 'Tapia', 'administrador', '123456', 'ADMINISTRADOR');

INSERT INTO persona(
    nombre,
    apellido,
    nombre_de_usuario,
    contrasenia,
    rol
) VALUES ('Luciana', 'Vega', 'estudiante', '123456', 'ESTUDIANTE');
INSERT INTO estudiante(
    id_persona,
    tipo_de_estudiante,
    facultad,
    carrera,
    base_inicio,
    autoseguro_activo,
    matriculado_semestre_actual,
    presenta_lesion,
    deporte,
    tipo_de_discapacidad,
    nivel_de_discapacidad,
    ultima_visita,
    visitante_concurrente,
    presenta_reservacion,
    presenta_penalidades,
    cantidad_penalidades,
    vetado_temporalmente,
    numero_de_puntos,
    nivel
) VALUES (2, 'Regular', 'FISI', 'Ingenieria de Software', 'B25', true, true, false, NULL, NULL, NULL, NULL, false, false, false, 0, false, 0, 0);

INSERT INTO persona(
    nombre,
    apellido,
    nombre_de_usuario,
    contrasenia,
    rol
) VALUES ('Lucas', 'Valdez', 'atleta', '123456', 'ESTUDIANTE');
INSERT INTO estudiante(
    id_persona,
    tipo_de_estudiante,
    facultad,
    carrera,
    base_inicio,
    autoseguro_activo,
    matriculado_semestre_actual,
    presenta_lesion,
    deporte,
    tipo_de_discapacidad,
    nivel_de_discapacidad,
    ultima_visita,
    visitante_concurrente,
    presenta_reservacion,
    presenta_penalidades,
    cantidad_penalidades,
    vetado_temporalmente,
    numero_de_puntos,
    nivel
) VALUES (3, 'Atleta', 'FII', 'Ingenieria Industrial', 'B20', true, true, false, 'Futbol', NULL, NULL, NULL, false, false, false, 0, false, 0, 0);

INSERT INTO persona(
    nombre,
    apellido,
    nombre_de_usuario,
    contrasenia,
    rol
) VALUES ('Luna', 'Velez', 'discapacitado', '123456', 'ESTUDIANTE');
INSERT INTO estudiante(
    id_persona,
    tipo_de_estudiante,
    facultad,
    carrera,
    base_inicio,
    autoseguro_activo,
    matriculado_semestre_actual,
    presenta_lesion,
    deporte,
    tipo_de_discapacidad,
    nivel_de_discapacidad,
    ultima_visita,
    visitante_concurrente,
    presenta_reservacion,
    presenta_penalidades,
    cantidad_penalidades,
    vetado_temporalmente,
    numero_de_puntos,
    nivel
) VALUES (4, 'discapacitado', 'FIEE', 'Ingenieria Electronica', 'B10', true, true, false, NULL, 'AUDITIVA', 'GRAVE', NULL, false, false, false, 0, false, 0, 0);