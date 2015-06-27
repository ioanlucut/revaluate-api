ALTER TABLE currencies
    ADD COLUMN locale character varying(255) NOT NULL default '';

/*Make the updates*/
UPDATE currencies SET locale = 'ar_AE' WHERE  currency_code = 'AED';
UPDATE currencies SET locale = 'ar_JO' WHERE  currency_code = 'JOD';
UPDATE currencies SET locale = 'ar_SY' WHERE  currency_code = 'SYP';
UPDATE currencies SET locale = 'hr_HR' WHERE  currency_code = 'HRK';
UPDATE currencies SET locale = 'fr_BE' WHERE  currency_code = 'EUR';
UPDATE currencies SET locale = 'es_PA' WHERE  currency_code = 'PAB';
UPDATE currencies SET locale = 'es_VE' WHERE  currency_code = 'VEF';
UPDATE currencies SET locale = 'zh_TW' WHERE  currency_code = 'TWD';
UPDATE currencies SET locale = 'da_DK' WHERE  currency_code = 'DKK';
UPDATE currencies SET locale = 'es_PR' WHERE  currency_code = 'USD';
UPDATE currencies SET locale = 'vi_VN' WHERE  currency_code = 'VND';
UPDATE currencies SET locale = 'sv_SE' WHERE  currency_code = 'SEK';
UPDATE currencies SET locale = 'es_BO' WHERE  currency_code = 'BOB';
UPDATE currencies SET locale = 'en_SG' WHERE  currency_code = 'SGD';
UPDATE currencies SET locale = 'ar_BH' WHERE  currency_code = 'BHD';
UPDATE currencies SET locale = 'ar_SA' WHERE  currency_code = 'SAR';
UPDATE currencies SET locale = 'ar_YE' WHERE  currency_code = 'YER';
UPDATE currencies SET locale = 'hi_IN' WHERE  currency_code = 'INR';
UPDATE currencies SET locale = 'sr_BA_#Latn' WHERE  currency_code = 'BAM';
UPDATE currencies SET locale = 'uk_UA' WHERE  currency_code = 'UAH';
UPDATE currencies SET locale = 'fr_CH' WHERE  currency_code = 'CHF';
UPDATE currencies SET locale = 'es_AR' WHERE  currency_code = 'ARS';
UPDATE currencies SET locale = 'ar_EG' WHERE  currency_code = 'EGP';
UPDATE currencies SET locale = 'ja_JP_JP_#u-ca-japanese' WHERE  currency_code = 'JPY';
UPDATE currencies SET locale = 'es_SV' WHERE  currency_code = 'SVC';
UPDATE currencies SET locale = 'pt_BR' WHERE  currency_code = 'BRL';
UPDATE currencies SET locale = 'is_IS' WHERE  currency_code = 'ISK';
UPDATE currencies SET locale = 'cs_CZ' WHERE  currency_code = 'CZK';
UPDATE currencies SET locale = 'pl_PL' WHERE  currency_code = 'PLN';
UPDATE currencies SET locale = 'sr_CS' WHERE  currency_code = 'CSD';
UPDATE currencies SET locale = 'ms_MY' WHERE  currency_code = 'MYR';
UPDATE currencies SET locale = 'es_CO' WHERE  currency_code = 'COP';
UPDATE currencies SET locale = 'bg_BG' WHERE  currency_code = 'BGN';
UPDATE currencies SET locale = 'es_PY' WHERE  currency_code = 'PYG';
UPDATE currencies SET locale = 'ar_SD' WHERE  currency_code = 'SDG';
UPDATE currencies SET locale = 'ro_RO' WHERE  currency_code = 'RON';
UPDATE currencies SET locale = 'en_PH' WHERE  currency_code = 'PHP';
UPDATE currencies SET locale = 'ar_TN' WHERE  currency_code = 'TND';
UPDATE currencies SET locale = 'es_GT' WHERE  currency_code = 'GTQ';
UPDATE currencies SET locale = 'ko_KR' WHERE  currency_code = 'KRW';
UPDATE currencies SET locale = 'es_MX' WHERE  currency_code = 'MXN';
UPDATE currencies SET locale = 'ru_RU' WHERE  currency_code = 'RUB';
UPDATE currencies SET locale = 'es_HN' WHERE  currency_code = 'HNL';
UPDATE currencies SET locale = 'zh_HK' WHERE  currency_code = 'HKD';
UPDATE currencies SET locale = 'no_NO_NY' WHERE  currency_code = 'NOK';
UPDATE currencies SET locale = 'hu_HU' WHERE  currency_code = 'HUF';
UPDATE currencies SET locale = 'th_TH' WHERE  currency_code = 'THB';
UPDATE currencies SET locale = 'ar_IQ' WHERE  currency_code = 'IQD';
UPDATE currencies SET locale = 'es_CL' WHERE  currency_code = 'CLP';
UPDATE currencies SET locale = 'ar_MA' WHERE  currency_code = 'MAD';
UPDATE currencies SET locale = 'tr_TR' WHERE  currency_code = 'TRY';
UPDATE currencies SET locale = 'ar_QA' WHERE  currency_code = 'QAR';
UPDATE currencies SET locale = 'ar_OM' WHERE  currency_code = 'OMR';
UPDATE currencies SET locale = 'sq_AL' WHERE  currency_code = 'ALL';
UPDATE currencies SET locale = 'es_DO' WHERE  currency_code = 'DOP';
UPDATE currencies SET locale = 'es_CU' WHERE  currency_code = 'CUP';
UPDATE currencies SET locale = 'en_NZ' WHERE  currency_code = 'NZD';
UPDATE currencies SET locale = 'sr_RS' WHERE  currency_code = 'RSD';
UPDATE currencies SET locale = 'es_UY' WHERE  currency_code = 'UYU';
UPDATE currencies SET locale = 'iw_IL' WHERE  currency_code = 'ILS';
UPDATE currencies SET locale = 'en_ZA' WHERE  currency_code = 'ZAR';
UPDATE currencies SET locale = 'en_AU' WHERE  currency_code = 'AUD';
UPDATE currencies SET locale = 'fr_CA' WHERE  currency_code = 'CAD';
UPDATE currencies SET locale = 'es_CR' WHERE  currency_code = 'CRC';
UPDATE currencies SET locale = 'ar_KW' WHERE  currency_code = 'KWD';
UPDATE currencies SET locale = 'ar_LY' WHERE  currency_code = 'LYD';
UPDATE currencies SET locale = 'ar_DZ' WHERE  currency_code = 'DZD';
UPDATE currencies SET locale = 'zh_CN' WHERE  currency_code = 'CNY';
UPDATE currencies SET locale = 'ar_LB' WHERE  currency_code = 'LBP';
UPDATE currencies SET locale = 'es_NI' WHERE  currency_code = 'NIO';
UPDATE currencies SET locale = 'mk_MK' WHERE  currency_code = 'MKD';
UPDATE currencies SET locale = 'be_BY' WHERE  currency_code = 'BYR';
UPDATE currencies SET locale = 'es_PE' WHERE  currency_code = 'PEN';
UPDATE currencies SET locale = 'in_ID' WHERE  currency_code = 'IDR';
UPDATE currencies SET locale = 'en_GB' WHERE  currency_code = 'GBP';