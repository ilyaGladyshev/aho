    let requestId = null;

     function getUserName(){
        try {
            fetch('/getCurrentName')
                .then(response => response.json())
                .then((responseStatus) => {
                    if (responseStatus.result){
                        responseStatus.listStatus.forEach((item) => {
                            document.getElementById('itemFioStatus').value = localStorage.getItem('userName');
                        })
                   }
                });
        } catch (err) {
           alert("Ошибка в коде ");
        }
        //const select = document.getElementById('itemFioStatus');
        //select.value = localStorage.getItem('userName');
     }

    function fillUsersStatus(){
        try {
            fetch('/usersController')
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemFioStatus');
                    if (commonData.result){
                        commonData.userDataList.forEach((item) => {
                            const option = document.createElement('option');
                            option.value = item.id;
                            option.textContent = item.name;
                            select.appendChild(option);
                        })
                    }
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    }

    function fillStatus(){
       try {
            fetch('/status')
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemStatus');
                    if (commonData.result){
                        commonData.listStatus.forEach((item) => {
                            const option = document.createElement('option');
                            option.value = item.name;
                            option.textContent = item.name;
                            select.appendChild(option);
                        })
                    }
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    }

    function correctRequest(){
        event.preventDefault();
        const params = new URLSearchParams(window.location.search) || {};
        const id = params.get('id');
        const userStatus           = document.getElementById('itemFioStatus').value;
        const status               = document.getElementById('itemStatus').value;
        const commentController    = document.getElementById('itemCommentController').value;
        if (userStatus == 0){
          alert('Заполнены не все поля!');
        } else {const requestData = {
                id: id,
                status: status,
                userStatus: userStatus,
                comment: commentController
            }
            fetch('/correctRequest',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
             })
             .then(response => response.json())
             .then(result => {
                if (result.result){
                    document.getElementById('responseMessage').textContent = "Запрос записан!";
                } else{
                    document.getElementById('responseMessage').textContent = "Запрос не записан!";
                }
             })
             .catch( error => {
                console.error('Ошибка отправки данных', error);
                document.getElementById('responseMessage').textContent = "Ошибка при отправки данных";
             });
        }
    }

    function changeStatus(){
        document.getElementById('itemFioStatus').value = getUserName();
    }

    function formatDateFromBase(str){
        const day = str.slice(0,2);
        const month = str.slice(3,5);
        const year = str.slice(6,10);
        let result = year + "-" + month + "-" + day;
        return result;
    }

    function voidDownloadFiles(){
          const formData = new FormData();
          formData.append('requestId', requestId);
          fetch('/downloadFiles', {
               method: 'POST',
               body: formData
          })
            .then(response => response.blob())
            .then(blob => {
            const url = window.URL.createObjectURL(blob);
            let filename = "archve_" + new Date().toISOString().split('T')[0] + ".zip";
            saveAs(url, filename);
            window.URL.revokeObjectURL(url);
          });
    }

    function saveAs(url, filename) {
        const a = document.createElement("a");
        a.href = url;
        a.download = filename || "file-name";
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }

    document.addEventListener("DOMContentLoaded", function() {
       const params = new URLSearchParams(window.location.search) || {};
       requestId = params.get('id');
       const downloadFiles = document.getElementById('itemStorageFiles');
       downloadFiles.addEventListener('click', voidDownloadFiles);
       const form = document.getElementById('dataRequest');
       form.addEventListener('submit', correctRequest);
       try {
            fillStatus();
            fillUsersStatus();
            const requestData = new URLSearchParams({
                id: encodeURIComponent(requestId)
            });
            const url       = `/correctRequestShow?${requestData.toString()}`;
            fetch(url)
               .then(response => response.json())
               .then((commonData) => {
                    if (commonData.result){
                        commonData.listJournalData.forEach((item) => {
                            const fioCreator = document.getElementById('itemFioCreator');
                            fioCreator.value = item.userCreator;
                            const area = document.getElementById('itemArea');
                            area.value = item.area;
                            const equipmentL1 = document.getElementById('itemEquipmentL1');
                            equipmentL1.value = item.category;
                            const equipmentL2 = document.getElementById('itemEquipmentL2');
                            equipmentL2.value = item.equipment;
                            const comment = document.getElementById('itemDescription');
                            comment.value = item.comment;
                            const normativeCount = document.getElementById('itemNormativeCount');
                            normativeCount.value = item.normativeCount;
                            const factCount = document.getElementById('itemFactCount');
                            factCount.value = item.factCount;
                            const spisCount = document.getElementById('itemSpisCount');
                            spisCount.value = item.spisCount;
                            const planedCount = document.getElementById('itemPlanedCount');
                            planedCount.value = item.planedCount;
                            const status = document.getElementById('itemStatus');
                            status.addEventListener('change', changeStatus);
                            status.value = item.status;
                            const userStatus = document.getElementById('itemFioStatus');
                            userStatus.value = item.userStatus;
                            const dateRequest = document.getElementById('itemDateRequest');
                            dateRequest.value = formatDateFromBase(item.dateRequest);
                            const commentController    = document.getElementById('itemCommentController');
                            commentController.value = item.commentController;
                            butJoinedFiles = document.getElementById('itemStorageFiles');
                            if (item.isJoinedFiles === 1){
                                butJoinedFiles.style.color = "blue";
                            } else {
                                butJoinedFiles.disabled = true;
                            }
                        })
                    }
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    });
