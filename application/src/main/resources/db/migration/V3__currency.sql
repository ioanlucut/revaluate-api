/*Do a cleanup*/

ALTER TABLE currencies
    ADD COLUMN numeric_code integer NOT NULL DEFAULT 0,
    ADD COLUMN display_name character varying(255) NOT NULL default '';

/*Update existing currencies*/
UPDATE currencies SET display_name='US Dollar', numeric_code=3 WHERE id=1;
UPDATE currencies SET display_name='Euro', numeric_code=978 WHERE id=2;

/*Make the updates*/
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(3, 'OMR', 'Omani Rial', 512);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(4, 'VEF', 'Venezuelan Bolívar', 937);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(5, 'CLP', 'Chilean Peso', 152);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(6, 'HUF', 'Hungarian Forint', 348);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(7, 'PYG', 'Paraguayan Guarani', 600);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(8, 'HKD', 'Hong Kong Dollar', 344);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(9, 'JOD', 'Jordanian Dinar', 400);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(10, 'DKK', 'Danish Krone', 208);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(11, 'ARS', 'Argentine Peso', 32);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(12, 'PAB', 'Panamanian Balboa', 590);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(13, 'PLN', 'Polish Zloty', 985);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(14, 'IDR', 'Indonesian Rupiah', 360);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(15, 'CZK', 'Czech Republic Koruna', 203);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(16, 'RUB', 'Russian Ruble', 643);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(17, 'SGD', 'Singapore Dollar', 702);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(18, 'DZD', 'Algerian Dinar', 12);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(19, 'AED', 'United Arab Emirates Dirham', 784);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(20, 'INR', 'Indian Rupee', 356);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(21, 'SDG', 'Sudanese Pound', 938);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(22, 'ZAR', 'South African Rand', 710);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(23, 'KWD', 'Kuwaiti Dinar', 414);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(24, 'TND', 'Tunisian Dinar', 788);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(25, 'ISK', 'Icelandic Króna', 352);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(26, 'SEK', 'Swedish Krona', 752);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(27, 'RON', 'Romanian Leu', 946);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(28, 'CRC', 'Costa Rican Colón', 188);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(29, 'MXN', 'Mexican Peso', 484);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(30, 'CAD', 'Canadian Dollar', 124);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(31, 'UYU', 'Uruguayan Peso', 858);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(32, 'MAD', 'Moroccan Dirham', 504);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(33, 'JPY', 'Japanese Yen', 392);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(34, 'RSD', 'Serbian Dinar', 941);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(35, 'MYR', 'Malaysian Ringgit', 458);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(36, 'QAR', 'Qatari Rial', 634);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(37, 'NZD', 'New Zealand Dollar', 554);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(38, 'PHP', 'Philippine Peso', 608);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(39, 'LYD', 'Libyan Dinar', 434);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(40, 'GBP', 'British Pound Sterling', 826);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(41, 'HNL', 'Honduran Lempira', 340);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(42, 'HRK', 'Croatian Kuna', 191);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(43, 'PEN', 'Peruvian Nuevo Sol', 604);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(44, 'IQD', 'Iraqi Dinar', 368);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(45, 'BGN', 'Bulgarian Lev', 975);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(46, 'CNY', 'Chinese Yuan', 156);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(47, 'SAR', 'Saudi Riyal', 682);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(48, 'GTQ', 'Guatemalan Quetzal', 320);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(49, 'TRY', 'Turkish Lira', 949);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(50, 'VND', 'Vietnamese Dong', 704);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(51, 'SVC', 'Salvadoran Colón', 222);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(52, 'LBP', 'Lebanese Pound', 422);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(53, 'BOB', 'Bolivian Boliviano', 68);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(54, 'CHF', 'Swiss Franc', 756);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(55, 'NOK', 'Norwegian Krone', 578);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(56, 'THB', 'Thai Baht', 764);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(57, 'ALL', 'Albanian Lek', 8);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(58, 'BYR', 'Belarusian Ruble', 974);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(59, 'CSD', 'Serbian Dinar (2002-2006)', 891);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(60, 'BHD', 'Bahraini Dinar', 48);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(61, 'KRW', 'South Korean Won', 410);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(62, 'NIO', 'Nicaraguan Córdoba', 558);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(63, 'BRL', 'Brazilian Real', 986);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(64, 'ILS', 'Israeli New Sheqel', 376);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(65, 'AUD', 'Australian Dollar', 36);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(66, 'EGP', 'Egyptian Pound', 818);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(67, 'MKD', 'Macedonian Denar', 807);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(68, 'CUP', 'Cuban Peso', 192);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(69, 'YER', 'Yemeni Rial', 886);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(70, 'UAH', 'Ukrainian Hryvnia', 980);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(71, 'BAM', 'Bosnia-Herzegovina Convertible Mark', 977);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(72, 'COP', 'Colombian Peso', 170);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(73, 'SYP', 'Syrian Pound', 760);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(74, 'TWD', 'New Taiwan Dollar', 901);
INSERT INTO currencies (id, currency_code, display_name, numeric_code) VALUES(75, 'DOP', 'Dominican Peso', 214);