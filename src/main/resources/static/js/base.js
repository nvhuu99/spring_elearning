var selectedId = null;
function selectRow(id) {
    $(`input[name="selected_id"][value="${id}"]`).prop('checked', true).trigger('change');
}
$(document).ready(function(){
    $(document).on('change', 'input[name="selected_id"]', function () {
        if (this.checked) {
            $('input[name="selected_id"]').not(this).prop('checked', false);
            selectedId = $(this).val()
        }
    });
})

function reloadWithoutPageParam() {
    const url = new URL(window.location.href);
    url.searchParams.delete('page');
    window.location.href = url.toString();
}
