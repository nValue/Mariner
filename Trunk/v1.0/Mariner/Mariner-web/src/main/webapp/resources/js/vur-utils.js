/* 
 * JavaScript utilidades varias proyecto VUR.
 * Andres Rivera
 * Version 1.0
 */

/**
 * Returns the version of Internet Explorer or a -1
 * (indicating the use of another browser).
 */
function getInternetExplorerVersion()
{
    var rv = -1; // Return value assumes failure.

    if (navigator.appName == 'Microsoft Internet Explorer')
    {
        var ua = navigator.userAgent;
        var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat(RegExp.$1);
    }

    return rv;
}
/**
 * Verificar navegador
 * @returns {undefined}
 */
function checkVersion() {
    var msg = "You're not using Internet Explorer.";
    var ver = getInternetExplorerVersion();

    if (ver > -1) {
        if (ver >= 9.0)
            msg = "You're using a recent copy of Internet Explorer."
        else
            msg = "You should upgrade your copy of Internet Explorer.";
            PF('loadingNavigator').show();
    }
    alert(msg);
}

