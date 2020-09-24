$(document).ready(function () {

    $('#addPersonForm, #editPersonForm, #addItemForm, #editItemForm').on('submit', function (e) {
        if(!this.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }
        $(this).addClass('was-validated');
    });

    //Opens Add Modal
    $('.add-person').click(function (e) {
        e.preventDefault();
        $('#name').val("");
        $('#email').val("");
        $('#addNewPersonModal').modal();
    });

    //Opens Edit Modal and fill the value
    $('.edit-person').click(function (e) {
        e.preventDefault();
        //Contains the href of <a> tag.
        let href = $(this).attr('href');

        //Sends get request to the server
        $.get(href, function (person, status) {
            //console.log(person);
            $('#idEdit').val(person.id);
            $('#nameEdit').val(person.name);
            $('#emailEdit').val(person.email);
        });

        $('#editNewPersonModal').modal();
    });

    //Opens Delete modal
    $('.delete-person, .delete-item, .delete-bill').click(function (e) {
        e.preventDefault();
        let href = $(this).attr('href');
        $('.delete-btn-confirm').attr('href', href);
        $('#deleteModal').modal();
    });

    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).remove();
        });
    }, 3000);


    /**
     * Item.html
     **/

    //Edit Item Form checkbox validation
    $('#addItemForm, #editItemForm').on('change', function (e) {
        let failed = false;
        if($('[name="sharingPersons"]:checked').length == 0) {
            $('[name="sharingPersons"]').attr("required", true);
            failed = true;
        }
        else {
            $('[name="sharingPersons"]').attr("required", false);
        }

        if(this.checkValidity() === false) {
            failed = true;
        }

        if(failed == true) {
            e.preventDefault();
            e.stopPropagation();
        }
        $(this).addClass('was-validated');
    });


    //Opens Add Modal
    $('.add-item').click(function (e) {
        e.preventDefault();
        $('#name').val("");
        $('#price').val("");
        $('.person-checkbox').prop("checked", false);
        $('#addNewItemModal').modal();
    });

    //Opens Edit Modal and fill the value
    $('.edit-item').click(function (e) {
        e.preventDefault();
        //Contains the href of <a> tag.
        let href = $(this).attr('href');

        //Sends get request to the server
        $.get(href, function (item, status) {
            //console.log(item);
            $('#idEdit').val(item.id);
            $('#nameEdit').val(item.name);
            $('#priceEdit').val(item.price);
            //Uncheck All Checkbox
            $('.person-checkbox').prop("checked", false);
            //Check only the selected field
            $.each(item.sharingPersons, function (key, value) {
                $("#"+value.id+"Edit").prop("checked",true);
            });
        });

        $('#editNewItemModal').modal();
    });

    /**
     * Bill.html
     */
    $('input[type="number"]').on('change', function () {

        $.ajax({
            type: "POST",
            data: $('#addBillForm').serialize(),
            url: "/bill/addBill?calculateTotal",
            success: function (response) {
                $('#total').val(response);
                $('#amount').val(response);
            }
        });
        //console.log("Changing");
    });

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })


});

