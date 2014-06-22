/***


Symbol	Meaning
-	If given, used as the position of the minus sign for negative numbers. If not given, the position to the left of the first number placeholder is used.
#	The placeholder for a number that does not imply zero padding.
0	The placeholder for a number that implies zero padding. If it is used to the right of a decimal separator, it implies trailing zeros, otherwise leading zeros.
,	The placeholder for a "thousands separator". May be used at most once, and it must be to the left of a decimal separator. Will be replaced by locale.separator in the result (the default is also ,).
.	The decimal separator. The quantity of # or 0 after the decimal separator will determine the precision of the result. If no decimal separator is present, the fractional precision is 0 -- meaning that it will be rounded to the nearest integer.
%	If present, the number will be multiplied by 100 and the % will be replaced by locale.percent.


assert( truncToFixed(0.12345, 4) == "0.1234" );
assert( roundToFixed(0.12345, 4) == "0.1235" );
assert( twoDigitAverage(1, 0) == "0" );
assert( twoDigitFloat(1.2345) == "1.23" );
assert( twoDigitFloat(1) == "1" );
assert( percentFormat(1.234567) == "123.46%" );
assert( numberFormatter("###,###%")(125) == "12,500%" );
assert( numberFormatter("##.000")(1.25) == "1.250" );

***/

_numberFormatter = function (placeholder, header, footer, locale, isPercent, precision, leadingZeros, separatorAt, trailingZeros) {
    return function (num) {
        num = parseFloat(num);
        if (typeof(num) == "undefined" || num === null || isNaN(num)) {
            return placeholder;
        }
        var curheader = header;
        var curfooter = footer;
        if (num < 0) {
            num = -num;
        } else {
            curheader = curheader.replace(/-/, "");
        }
        var me = arguments.callee;
        var fmt = formatLocale(locale);
        if (isPercent) {
            num = num * 100.0;
            curfooter = fmt.percent + curfooter;
        }
        num = roundToFixed(num, precision);
        var parts = num.split(/\./);
        var whole = parts[0];
        var frac = (parts.length == 1) ? "" : parts[1];
        var res = "";
        while (whole.length < leadingZeros) {
            whole = "0" + whole;
        }
        if (separatorAt) {
            while (whole.length > separatorAt) {
                var i = whole.length - separatorAt;
                res = fmt.separator + whole.substring(i, whole.length) + res;
                whole = whole.substring(0, i);
            }
        }
        res = whole + res;
        if (precision > 0) {
            while (frac.length < trailingZeros) {
                frac = frac + "0";
            }
            res = res + fmt.decimal + frac;
        }
        return curheader + res + curfooter;
    };
};

numberFormatter = function (pattern, placeholder/* = "" */, locale/* = "default" */) {
    // http://java.sun.com/docs/books/tutorial/i18n/format/numberpattern.html
    // | 0 | leading or trailing zeros
    // | # | just the number
    // | , | separator
    // | . | decimal separator
    // | % | Multiply by 100 and format as percent
    if (typeof(placeholder) == "undefined") {
        placeholder = "";
    }
    var match = pattern.match(/((?:[0#]+,)?[0#]+)(?:\.([0#]+))?(%)?/);
    if (!match) {
        throw TypeError("Invalid pattern");
    }
    var header = pattern.substr(0, match.index);
    var footer = pattern.substr(match.index + match[0].length);
    if (header.search(/-/) == -1) {
        header = header + "-";
    }
    var whole = match[1];
    var frac = (typeof(match[2]) == "string" && match[2] != "") ? match[2] : "";
    var isPercent = (typeof(match[3]) == "string" && match[3] != "");
    var tmp = whole.split(/,/);
    var separatorAt;
    if (typeof(locale) == "undefined") {
        locale = "default";
    }
    if (tmp.length == 1) {
        separatorAt = null;
    } else {
        separatorAt = tmp[1].length;
    }
    var leadingZeros = whole.length - whole.replace(/0/g, "").length;
    var trailingZeros = frac.length - frac.replace(/0/g, "").length;
    var precision = frac.length;
    var rval = _numberFormatter(
        placeholder, header, footer, locale, isPercent, precision,
        leadingZeros, separatorAt, trailingZeros
    );
    
    return rval;
};

formatLocale = function (locale) {
    if (typeof(locale) == "undefined" || locale === null) {
        locale = "default";
    }
    if (typeof(locale) == "string") {
        var rval = LOCALE[locale];
        if (typeof(rval) == "string") {
            rval = arguments.callee(rval);
            LOCALE[locale] = rval;
        }
        return rval;
    } else {
        return locale;
    }
};

twoDigitAverage = function (numerator, denominator) {
    if (denominator) {
        var res = numerator / denominator;
        if (!isNaN(res)) {
            return twoDigitFloat(res);
        }
    }
    return "0";
};

twoDigitFloat = function (aNumber) {
    var res = roundToFixed(aNumber, 2);
    if (res.indexOf(".00") > 0) {
        return res.substring(0, res.length - 3);
    } else if (res.charAt(res.length - 1) == "0") {
        return res.substring(0, res.length - 1);
    } else {
        return res;
    }
};

lstrip = function (str, /* optional */chars) {
    str = str + "";
    if (typeof(str) != "string") {
        return null;
    }
    if (!chars) {
        return str.replace(/^\s+/, "");
    } else {
        return str.replace(new RegExp("^[" + chars + "]+"), "");
    }
};

rstrip = function (str, /* optional */chars) {
    str = str + "";
    if (typeof(str) != "string") {
        return null;
    }
    if (!chars) {
        return str.replace(/\s+$/, "");
    } else {
        return str.replace(new RegExp("[" + chars + "]+$"), "");
    }
};

strip = function (str, /* optional */chars) {
    var self = Format;
    return self.rstrip(self.lstrip(str, chars), chars);
};

truncToFixed = function (aNumber, precision) {
    var fixed = _numberToFixed(aNumber, precision);
    var fracPos = fixed.indexOf(".");
    if (fracPos > 0 && fracPos + precision + 1 < fixed.length) {
        fixed = fixed.substring(0, fracPos + precision + 1);
        fixed = _shiftNumber(fixed, 0);
    }
    return fixed;
};

roundToFixed = function (aNumber, precision) {
    var fixed = _numberToFixed(aNumber, precision);
    var fracPos = fixed.indexOf(".");
    if (fracPos > 0 && fracPos + precision + 1 < fixed.length) {
        var str = _shiftNumber(fixed, precision);
        str = _numberToFixed(Math.round(parseFloat(str)), 0);
        fixed = _shiftNumber(str, -precision);
    }
    return fixed;
};

/**
 * Converts a number to a fixed format string. This function handles
 * conversion of exponents by shifting the decimal point to the left
 * or the right. It also guarantees a specified minimum number of
 * fractional digits (but no maximum).
 *
 * @param {Number} aNumber the number to convert
 * @param {Number} precision the minimum number of decimal digits
 *
 * @return {String} the fixed format number string
 */
_numberToFixed = function (aNumber, precision) {
    var str = aNumber.toString();
    var parts = str.split(/[eE]/);
    var exp = (parts.length === 1) ? 0 : parseInt(parts[1], 10) || 0;
    var fixed = _shiftNumber(parts[0], exp);
    parts = fixed.split(/\./);
    var whole = parts[0];
    var frac = (parts.length === 1) ? "" : parts[1];
    while (frac.length < precision) {
        frac += "0";
    }
    if (frac.length > 0) {
        return whole + "." + frac;
    } else {
        return whole;
    }
};

/**
 * Shifts the decimal dot location in a fixed format number string.
 * This function handles negative values and will add and remove
 * leading and trailing zeros as needed.
 *
 * @param {String} num the fixed format number string
 * @param {Number} exp the base-10 exponent to apply
 *
 * @return {String} the new fixed format number string
 */
_shiftNumber = function (num, exp) {
    var pos = num.indexOf(".");
    if (pos < 0) {
        pos = num.length;
    } else {
        num = num.substring(0, pos) + num.substring(pos + 1);
    }
    pos += exp;
    while (pos <= 0 || (pos <= 1 && num.charAt(0) === "-")) {
        if (num.charAt(0) === "-") {
            num = "-0" + num.substring(1);
        } else {
            num = "0" + num;
        }
        pos++;
    }
    while (pos > num.length) {
        num += "0";
    }
    if (pos < num.length) {
        num = num.substring(0, pos) + "." + num.substring(pos);
    }
    while (/^0[^.]/.test(num)) {
        num = num.substring(1);
    }
    while (/^-0[^.]/.test(num)) {
        num = "-" + num.substring(2);
    }
    return num;
};

percentFormat = function (aNumber) {
    return twoDigitFloat(100 * aNumber) + '%';
};

LOCALE = {
    en_US: {separator: ",", decimal: ".", percent: "%"},
    de_DE: {separator: ".", decimal: ",", percent: "%"},
    pt_BR: {separator: ".", decimal: ",", percent: "%"},
    fr_FR: {separator: " ", decimal: ",", percent: "%"},
    "default": "en_US",
    __export__: false
};



