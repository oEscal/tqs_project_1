$(document).ready(function() {

    let type_html = $('#type');
    let hours_html = $('#hours_component');

    // init type always with same value
    type_html.val("current");

    // change type -> hours appear or disappear
    type_html.change(function () {
        let hours_input = $("#hours");
        if (this.value === "forecast" || this.value === "history"){
            hours_html.show();
            hours_input.prop('required', true);
        }
        else {
            hours_html.hide();
            hours_input.prop('required', false);
        }
    });
});
