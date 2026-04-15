    let status;

    function showClosedJournal(){
          const params = new URLSearchParams({
               statistics: 4
          });
          const requestUrl = `journal.html?${params.toString()}`;;
          window.open(requestUrl, '_blank');
    }

    function showStatistic(){
          const params = new URLSearchParams({
               statistics: 3
          });
          const requestUrl = `journal.html?${params.toString()}`;;
          window.open(requestUrl, '_blank');
    }

    function showCancel(){
          const params = new URLSearchParams({
               statistics: 2
          });
          const requestUrl = `journal.html?${params.toString()}`;;
          window.open(requestUrl, '_blank');
    }

    function showFormClosing(){
          const requestUrl = `/html/closing.html`;
          window.open(requestUrl, '_blank');
    }

    function showJournal(){
          const requestUrl = `/html/journal.html`;
          window.open(requestUrl, '_blank');
    }

    function showRegistration(){
          const requestUrl = `/html/requestAdd.html`;
          window.open(requestUrl, '_blank');
    }

    function getStatus(){
        /*try {
            fetch('/getCurrentStatus')
                  .then(response => response.json())
                  .then((responseStatus) => {
                   if (responseStatus.result){
                        responseStatus.listStatus.forEach((item) => {
                            status = item.name;
                        })
                   }
                   fillForm();
            });
        } catch (err) {
            alert("Ошибка в коде ");
        }*/
        status = localStorage.getItem('userStatus');
        fillForm();
    }

    function fillForm(){
         const menuJournal = document.getElementById('itemMenuJournal');
         menuJournal.addEventListener('click', showJournal);

         const menuRequest = document.getElementById('itemMenuRequest');
         menuRequest.addEventListener('click', showRegistration);
         if (status == "2"){
            menuRequest.style.display = "none";
         }

         const menuStatistic = document.getElementById('itemMenuStatistic');
         menuStatistic.addEventListener('click', showStatistic);

        const menuCancel = document.getElementById('itemMenuCancel');
        menuCancel.addEventListener('click', showCancel);

        const menuClosedJournal = document.getElementById('itemMenuClosed');
        menuClosedJournal.addEventListener('click', showClosedJournal);

         const menuClosing = document.getElementById('itemMenuClosing');
         menuClosing.addEventListener('click', showFormClosing);
         if (status != "3"){
            menuClosing.style.display = "none";
         }
    }

    document.addEventListener("DOMContentLoaded", function() {
         getStatus();
    });

    function logout(){
    }
