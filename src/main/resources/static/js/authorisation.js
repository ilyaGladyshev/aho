    function fillUsers(){
         try {
            fetch('/usersVerification')
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemSelect');
                    if (commonData.result){
                        commonData.userDataList.forEach((item) => {
                            const option = document.createElement('option');
                            option.value = item.id;
                            option.textContent = item.name;
                            select.appendChild(option);
                        })
                    }
                    select.value = localStorage.getItem('userName');
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    }

    function verification(){
            event.preventDefault();

            const itemId    = document.getElementById('itemSelect').value;
            const itemText  = document.getElementById('itemInput').value;

            if (!itemId || !itemText){
                document.getElementById('responseMessage').textContent = "Пожалуйста, укажите логин и пароль!";
                return;
            }

            const commonData = {
                itemId: itemId,
                itemText: itemText
            }

            fetch('/verification', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(commonData)
             })
             .then(response => response.json())
             .then(result => {
                if (result.result){
                    document.getElementById('responseMessage').textContent = "Авторизация выполнена!";
                    localStorage.removeItem('userName');
                    localStorage.removeItem('userStatus');
                    localStorage.setItem('authToken', result.token);
                    localStorage.setItem('userName', commonData.itemId);
                    localStorage.setItem('userStatus', result.status);
                    localStorage.setItem('userId', result.id);
                    const params = new URLSearchParams({
                         itemId: itemId,
                         itemText: itemText
                    });
                    const requestUrl = `/html/mainMenu.html`;
                    window.open(requestUrl, '_blank');
                } else{
                    document.getElementById('responseMessage').textContent = "Авторизация не выполнена!";
                }
             })
             .catch( error => {
                console.error('Ошибка отправки данных', error);
                document.getElementById('responseMessage').textContent = "Ошибка при отправки данных";
             });
         }

    document.addEventListener("DOMContentLoaded", function() {
         fillUsers();

         const form = document.getElementById('dataForm');
         form.addEventListener('submit', verification);
    });

