/* 
 * Utilidades JQuery para manejo de efectos y transacciones en pagina de generacion certificados SNR.
 * 
 * @autor   Jairo Andres Rivera
 * @version 1.0
 * @date    05/06/2015
 */
$(function () {
    $("#menuicon").click(function () {
        $('#menuContainer').toggle("slide", {direction: "left"}, 300);
    });
    
    $("#invisMenu").click(function () {
        $('#menuContainer').hide('slide', {direction: 'left'},300);
    });


    $(document).keyup(function (event) {
        if (event.which === 27) {
            $('#menuContainer').hide('slide');
        }
    });

    //Sobreescribe el componente de PrimeFaces para el focus en los dialogs cuando hay un calendar
    // así el sistema no despliega dicho Calendar.  
    PrimeFaces.widget.Dialog.prototype.applyFocus = function () {
        var firstInput = this.jq.find(':not(:submit):not(:button):input:visible:enabled:first');
        if (!firstInput.hasClass('hasDatepicker')) {
            firstInput.focus();
        }
    }
});
