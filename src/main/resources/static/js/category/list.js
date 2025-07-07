function openEditModal(id) {
    selectRow(id)

    $('#editModal input[name="name"]').val('')
    $('#editModal .is-invalid').removeClass('is-invalid')
    $('#editModal .invalid-feedback').remove()

    $.get('/api/categories/' + selectedId, function (res) {
        if (res.success) {
            $('#editModal input[name="name"]').val(res.data.name)
        } else {
            alert("Failed to load category data.")
        }
    }).fail(function () {
        alert("Error loading category.")
    })
}

$(document).ready(function(){
	$('#btn-delete').click(() => {
	    if (!selectedId) {
	        return
	    }
	    $.ajax({
	        url: '/api/categories/' + selectedId,
	        type: 'DELETE',
	        success: function(result) {
                reloadWithoutPageParam()
            },
            error: function(xhr) {
                reloadWithoutPageParam()
            }
        })
	})
	
	$('#addModal input[type="submit"]').on('click', function () {
        // Clear previous errors
        $('#addModal .is-invalid').removeClass('is-invalid')
        $('#addModal .invalid-feedback').remove()

        const name = $('#addModal input[name="name"]').val()

        $.ajax({
            url: '/api/categories',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ name: name }),
            success: function () {
                $('#addModal').modal('hide')
                reloadWithoutPageParam()
            },
            error: function (xhr) {
                if (xhr.status === 400 && xhr.responseJSON?.errors) {
                    const errors = xhr.responseJSON.errors
                    for (const field in errors) {
                        const input = $('#addModal [name="' + field + '"]')
                        input.addClass('is-invalid')
                        input.after('<div class="invalid-feedback">' + errors[field].message + '</div>')
                    }
                } else {
                    alert("An unexpected error occurred.")
                }
            }
        })
    })
    
    $('#editModal input[type="submit"]').on('click', function () {
        $('#editModal .is-invalid').removeClass('is-invalid')
        $('#editModal .invalid-feedback').remove()

        const name = $('#editModal input[name="name"]').val()

        $.ajax({
            url: '/api/categories/' + selectedId,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({ name: name }),
            success: function () {
                $('#editModal').modal('hide')
                reloadWithoutPageParam()
            },
            error: function (xhr) {
                if (xhr.status === 400 && xhr.responseJSON?.errors) {
                    const errors = xhr.responseJSON.errors
                    for (const field in errors) {
                        const input = $('#editModal [name="' + field + '"]')
                        input.addClass('is-invalid')
                        input.after('<div class="invalid-feedback">' + errors[field].message + '</div>')
                    }
                } else {
                    alert("Failed to update category.")
                }
            }
        })
    })
})