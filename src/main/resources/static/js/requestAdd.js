    let requestId = null;
    let oldItem = null;

    function fillArea(){
        try {
            fetch('/areasRequest')
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemArea');
                    if (commonData.result){
                        commonData.listArea.forEach((item) => {
                            const option = document.createElement('option');
                            option.value = item.id;
                            option.textContent = item.name;
                            select.appendChild(option);
                        })
                    }
                    select.value = "";
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    }

    function fillUsersCreator(){
        try {
             fetch('/usersCreator')
                   .then(response => response.json())
                   .then((commonData) => {
                         const select = document.getElementById('itemFioCreator');
                         if (commonData.result){
                              commonData.userDataList.forEach((item) => {
                                  const option = document.createElement('option');
                                  option.value = item.id;
                                  option.textContent = item.name;
                                  select.appendChild(option);
                              })
                       }
                       select.value =  localStorage.getItem('userName');
                   });
         } catch (err) {
           alert("Ошибка в коде ");
         }
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

    function fillEquipmentL1(){
       try {
            fetch('/equipmentL1')
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemEquipmentL1');
                    if (commonData.result){
                        commonData.listEquipment.forEach((item) => {
                            if (item.name != "Работы и услуги"){
                                const option = document.createElement('option');
                                option.value = item.id;
                                option.textContent = item.name;
                                select.appendChild(option);
                            }
                        })
                    }
                    if (oldItem != null){
                        select.value = oldItem.category;
                    }
                      else select.value = "";
                    fillEquipmentL2();
               });
         } catch (err) {
           alert("Ошибка в коде ");
         }
    }

    function voidOther(){
         const other = document.getElementById('itemOther');
         const select = document.getElementById('itemEquipmentL2');
         if (other.value === ""){
             select.disabled = false;
         } else {
             select.value = "";
             select.disabled = true;
         }
    }

    function fillEquipmentL2(){
       try {
            let equipmentName = document.getElementById('itemEquipmentL1').value;
            const equipmentData = new URLSearchParams({
                idCategory: encodeURIComponent(equipmentName)
            });
            const url = `/equipmentL2?${equipmentData.toString()}`;
            fetch(url)
               .then(response => response.json())
               .then((commonData) => {
                    const select = document.getElementById('itemEquipmentL2');
                    while (select.options.length > 0){
                      select.remove(0);
                    }
                    if (commonData.result){
                        commonData.listEquipment.forEach((item) => {
                            const option = document.createElement('option');
                            option.value = item.id;
                            option.textContent = item.name;
                            select.appendChild(option);
                        })
                    }
                    select.disabled = false;
                    const other = document.getElementById('itemOther');
                    other.value = "";
                    if (oldItem != null){
                        select.value = oldItem.equipment;
                    }
                      else select.value = "";
               });
               if ((document.getElementById('itemEquipmentL1').value === "Прочее оборудование")
               ||(document.getElementById('itemEquipmentL1').value === "Строительные материалы")){
                    const tdHeader = document.getElementById('tdHeader');
                    tdHeader.style.display = '';
                    const tdItem = document.getElementById('tdItem');
                    tdItem.style.display = '';
                    const tdEquipnemt =  document.getElementById('tdEquipmentName');
                    tdEquipnemt.setAttribute('colspan', 0);
               } else {
                     const tdHeader = document.getElementById('tdHeader');
                     tdHeader.style.display = "none";
                     const tdItem = document.getElementById('tdItem');
                     tdItem.style.display = "none";
                     const tdEquipnemt =  document.getElementById('tdEquipmentName');
                     tdEquipnemt.setAttribute('colspan', 3);
               }
        } catch (err) {
           alert("Ошибка при заполнении наименований оборудования ");
        }
    }

    function fillDateRequest(){
        const now = new Date();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const dateRequest = document.getElementById('itemDateRequest');
        dateRequest.value = now.getFullYear() + "-" + month + "-" + day;
    }

    function formatDate(date){
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hour = String(date.getHours()).padStart(2, '0');
        const minute = String(date.getMinutes()).padStart(2, '0');
        const second = String(date.getSeconds()).padStart(2, '0');
        return year + "-" + month + "-" + day + " " + hour + ':' + minute + ':' + second;
    }

    function formatDateFromBase(str){
        const day = str.slice(0,2);
        const month = str.slice(3,5);
        const year = str.slice(6,10);
        let result = year + "-" + month + "-" + day;
        return result;
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

    function setDisabled(){
          const area = document.getElementById('itemArea');
          const equipmentL1 = document.getElementById('itemEquipmentL1');
          const equipmentL2 = document.getElementById('itemEquipmentL2');
          const comment = document.getElementById('itemDescription');
          const normativeCount = document.getElementById('itemNormativeCount');
          const planedCount = document.getElementById('itemPlanedCount');
          const factCount = document.getElementById('itemFactCount');
          const spisCount = document.getElementById('itemSpisCount');
          const other          = document.getElementById('itemOther');
          area.disabled     = true;
          comment.disabled  = true;
          normativeCount.disabled = true;
          spisCount.disabled   = true;
          factCount.disabled   = true;
          planedCount.disabled = true;
          equipmentL1.disabled = true;
          equipmentL2.disabled = true;
          other.disabled       = true;
    }

    function fillForm(){
            try{
                const requestData = new URLSearchParams({
                    id: encodeURIComponent(requestId)
                });
                const url = `/correctRequestShow?${requestData.toString()}`;
                fetch(url)
                   .then(response => response.json())
                   .then((commonData) => {
                        if (commonData.result){
                            commonData.listJournalData.forEach((item) => {
                                oldItem = item;
                                const dateRequest = document.getElementById('itemDateRequest');
                                dateRequest.value = formatDateFromBase(item.dateRequest);
                                const fioCreator = document.getElementById('itemFioCreator');
                                fioCreator.value = item.userCreator;
                                const area = document.getElementById('itemArea');
                                area.value = item.area;
                                const equipmentL1 = document.getElementById('itemEquipmentL1');
                                fillEquipmentL1();
                                const comment = document.getElementById('itemDescription');
                                comment.value = item.comment;
                                const normativeCount = document.getElementById('itemNormativeCount');
                                normativeCount.value = item.normativeCount;
                                const planedCount = document.getElementById('itemPlanedCount');
                                planedCount.value = item.planedCount;
                                const factCount = document.getElementById('itemFactCount');
                                factCount.value = item.factCount;
                                const spisCount = document.getElementById('itemSpisCount');
                                spisCount.value = item.spisCount;
                                const equipmentL2 = document.getElementById('itemEquipmentL2');
                                const status = document.getElementById('itemStatus');
                                status.value = item.status;
                                const userStatus = document.getElementById('itemFioStatus');
                                userStatus.value = item.userStatus;
                                const commentController    = document.getElementById('itemCommentController');
                                commentController.value = item.commentController;
                                fioCreator.disabled = true;
                                if (item.status != "На доработку"){
                                    setDisabled();
                                }
                                divJoinedFiles = document.getElementById('idStorageFiles');
                                if (item.isJoinedFiles === 0){
                                   divJoinedFiles.style.display = "none";
                                } else {
                                   downloadFiles = document.getElementById('itemStorageFiles');
                                   downloadFiles.addEventListener('click', voidDownloadFiles);
                                }
                            })
                        }
                   });
         } catch (err) {
           alert("Ошибка в коде ");
         }
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

    function getCountByText(s){
        if (s === ""){
            return 0;
        } else {
            return s;
        }
    }

    function addRequest(){
        event.preventDefault();
        const status = document.getElementById('itemStatus').value;
        let rId;
        if (requestId == null) {
            rId = 0;
        } else {
            rId = requestId;
        }
        if ((requestId == null) || (status === "На доработку")) {
            const now = new Date();
            const dateRequest    = formatDate(now);
            const fioCreator     = document.getElementById('itemFioCreator').value;
            const area           = document.getElementById('itemArea').value;
            const user           = document.getElementById('itemFioCreator').value;
            const equipmentL1    = document.getElementById('itemEquipmentL1').value;
            let equipmentL2    = document.getElementById('itemEquipmentL2').value;
            const description    = document.getElementById('itemDescription').value;
            const normativeCount = getCountByText(document.getElementById('itemNormativeCount').value);
            const factCount      = getCountByText(document.getElementById('itemFactCount').value);
            const spisCount      = getCountByText(document.getElementById('itemSpisCount').value);
            const planedCount    = getCountByText(document.getElementById('itemPlanedCount').value);
            const other          = document.getElementById('itemOther').value;
            if (!(other === "")){
                const equipmentData = {
                    name: other,
                    category: equipmentL1,
                };
                fetch('/addEquipment', {
                     method: 'POST',
                     headers: {
                         'Content-Type': 'application/json'
                     },
                     body: JSON.stringify(equipmentData)
                })
                .then(response => response.json())
                .then(result => {
                    if (result.result){
                    document.getElementById('responseMessage').textContent = "Оборудованик записано!";
                    const requestData = {
                          dateRequest: dateRequest,
                          fioCreator:  fioCreator,
                          user:        user,
                          area:        area,
                          equipmentL1: equipmentL1,
                          equipmentL2: other,
                          description: description,
                          normativeCount: normativeCount,
                          factCount     : factCount,
                          spisCount     : spisCount,
                          planedCount   : planedCount,
                          requestId     : rId,
                          }
                          writeRequest(requestData);
                    } else{
                        document.getElementById('responseMessage').textContent = "Оборудование не записано!";
                    }
                });
            }
            if (other === ""){
                 if ((area == 0) || (equipmentL1 == 0) || (!((equipmentL2 != "") || (description != ""))) ){
                   alert('Заполнены не все поля!');
                 } else {
                     const requestData = {
                         dateRequest: dateRequest,
                         fioCreator:  fioCreator,
                         user:        user,
                         area:        area,
                         equipmentL1: equipmentL1,
                         equipmentL2: equipmentL2,
                         description: description,
                         normativeCount: normativeCount,
                         factCount     : factCount,
                         spisCount     : spisCount,
                         planedCount   : planedCount,
                         requestId     : rId,
                     }
                     writeRequest(requestData);
                 }
            }
        }   else {
            alert('Заявка уже внесена!');
        }


    }

    function writeRequest(requestData){
        fetch('/addRequest', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
    .then(response => response.json())
    .then(journalData => {
     if (journalData.result){
          setDisabled();
          document.getElementById('responseMessage').textContent = "Запрос записан!";
          journalData.listJournalData.forEach((item) => {
            requestId = item.id;
          });
     } else{
          document.getElementById('responseMessage').textContent = "Запрос не записан!";
     }
     })
     .catch( error => {
           console.error('Ошибка отправки данных', error);
            document.getElementById('responseMessage').textContent = "Ошибка при отправки данных";
     });
    }

    function voidSelectFiles(){
        const fileInput = document.getElementById('idFileInput');
        if (requestId != null){
            fileInput.click();
        } else{
            alert("Для приобщения изображений вначале сохраните заявку!");
        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        const params = new URLSearchParams(window.location.search) || {};
        if (params != null){
            requestId = params.get('id');
        }
        fillDateRequest();
        fillArea();
        fillUsersCreator();
        if (requestId != null){
          fillForm();
        } else {
          fillEquipmentL1();
          divJoinedFiles = document.getElementById('idStorageFiles');
          divJoinedFiles.style.display = "none";
        }
        const fileInput = document.getElementById('idFileInput');
        fileInput.addEventListener('change', (event) => {
            const files = event.target.files;
            if (files.length === 0){
                console.log('Файлы не выбраны!');
                return;
            }
            if (requestId != null){
                try {
                     const formData = new FormData();
                     formData.append('requestId', requestId);
                     for (let i = 0; i < files.length; i++){
                        formData.append('files', files[i]);
                    }
                    fetch('/writeFiles', {
                        method: 'POST',
                        body: formData
                    })
                    .then(response => response.json())
                    .then(journalData => {
                    if (journalData.result){
                      document.getElementById('responseMessage').textContent = "Файл записан!";
                    } else{
                      document.getElementById('responseMessage').textContent = "Файл не записан!";
                    }
                    })
                } catch (err) {
                    alert("Ошибка при записи файлов");
                }
            }
        });
        const selectFiles = document.getElementById('itemSelectFiles');
        selectFiles.addEventListener('click', voidSelectFiles);
        const equipmentL1 = document.getElementById('itemEquipmentL1');
        equipmentL1.addEventListener('change', fillEquipmentL2);
        const form = document.getElementById('dataRequest');
        form.addEventListener('submit', addRequest);
        const other = document.getElementById('itemOther');
        other.addEventListener('change', voidOther);
        const tdHeader = document.getElementById('tdHeader');
        tdHeader.style.display = "none";
        const tdItem = document.getElementById('tdItem');
        tdItem.style.display = "none";
        const tdEquipnemt =  document.getElementById('tdEquipmentName');
        tdEquipnemt.setAttribute('colspan', 3);

    });