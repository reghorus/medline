$(".date_picker").datepicker({
    dateFormat: 'M dd, yy'
});

$('#parts>thead>tr>th').click(function () {
    var columnName = $(this).attr('columnname');
    $.ajax({
        url: '/parts?sort_field=' + columnName,
        type: 'GET',
        data: $(this).serialize(),
        success: function (data) {
            showParts(data)
        }
    });
});

$('#filter_form').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: '/parts',
        type: 'GET',
        data: $(this).serialize(),
        success: function (data) {
            showParts(data);
        }
    });
});

function showParts(parts) {
    $('#parts>tbody').html('');
    $('#parts>tbody').html(parts);
}