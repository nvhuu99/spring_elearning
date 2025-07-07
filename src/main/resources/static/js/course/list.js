function openEditModal(id) {
  selectRow(id)

  $("#editCourseTitle").val("");
  $("#editCourseDays").val("");
  $("#editCourseHours").val("");
  $("#editCourseCategory").val("");
  $("#editModal .is-invalid").removeClass("is-invalid");
  $("#editModal .invalid-feedback").remove();

  $.get(`/api/courses/${selectedId}`)
    .done(function (res) {
      if (res.success) {
        $("#editCourseTitle").val(res.data.title);
        $("#editCourseDays").val(res.data.days);
        $("#editCourseHours").val(res.data.hours);
        $("#editCourseCategory").val(res.data.categoryId);
        $("#editCourseModal").modal("show");
      } else {
        alert("Failed to load course data.");
      }
    })
    .fail(function () {
      alert("Error fetching course details.");
    });
}


$(document).ready(function(){
	$('#btn-delete').click(() => {
	    if (!selectedId) {
	        return
	    }
	    $.ajax({
	        url: '/api/courses/' + selectedId,
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
      $("#addCourseForm .is-invalid").removeClass("is-invalid");
      $("#addCourseForm .invalid-feedback").remove();

      const payload = {
        title: $("#courseTitle").val(),
        days: parseInt($("#courseDays").val(), 10),
        hours: parseInt($("#courseHours").val(), 10),
        categoryId: parseInt($("#courseCategory").val(), 10),
      };

      $.ajax({
        url: "/api/courses",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function () {
          $("#addCourseModal").modal("hide");
          reloadWithoutPageParam();
        },
        error: function (xhr) {
          if (xhr.status === 400 && xhr.responseJSON?.errors) {
            const errors = xhr.responseJSON.errors;
            // for each field error, mark input and insert feedback
            for (const field in errors) {
              const $input = $('#addModal [name="' + field + '"]');
              $input.addClass("is-invalid");
              $input.after(
                '<div class="invalid-feedback">' + errors[field].message + "</div>"
              );
            }
          } else {
            alert("Unexpected error. Please try again.");
          }
        },
      });
    });

    $('#editModal input[type="submit"]').on('click', function () {
      $('#editCourseForm .is-invalid').removeClass('is-invalid');
      $('#editCourseForm .invalid-feedback').remove();

      const payload = {
        title: $('#editCourseTitle').val(),
        days: parseInt($('#editCourseDays').val(), 10),
        hours: parseInt($('#editCourseHours').val(), 10),
        categoryId: parseInt($('#editCourseCategory').val(), 10)
      };

      $.ajax({
        url: '/api/courses/'+selectedId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(payload)
      })
      .done(function() {
        $('#editCourseModal').modal('hide');
        reloadWithoutPageParam();
      })
      .fail(function(xhr) {
        if (xhr.status === 400 && xhr.responseJSON?.errors) {
          const errors = xhr.responseJSON.errors;
          for (const field in errors) {
            const msg = errors[field].message || errors[field];
            const $input = $(`#editCourseForm [name="${field}"]`);
            $input.addClass('is-invalid');
            $input.after(`<div class="invalid-feedback">${msg}</div>`);
          }
        } else {
          alert("Failed to update course.");
        }
      });
    });
})