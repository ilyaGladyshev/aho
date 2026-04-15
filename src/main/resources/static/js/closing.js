    function closingRequest(){
        fetch('/closing')
            .then(response => response.json())
            .then((commonData) => {
                if (commonData.result){
                    alert('Закрытие заявок завершено!');
                }
            });
    }

    document.addEventListener("DOMContentLoaded", function() {
         const buttonClosing = document.getElementById('itemButtonClosing');
         buttonClosing.addEventListener('click', closingRequest);
    });