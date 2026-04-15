        let globalStatus;
        let journalParams = "0";

        function fillTable(){
            const table           = document.getElementById('tableBody');
            const dateFrom        = document.getElementById('itemDateFrom').value;
            const dateTo          = document.getElementById('itemDateTo').value;
            const area            = document.getElementById('itemArea').value;
            const userCreator     = document.getElementById('itemFioCreator').value;
            const userInspector   = document.getElementById('itemFioInspector').value;
            const category        = document.getElementById('itemEquipmentL1').value;
            const equipment       = document.getElementById('itemEquipmentL2').value;
            const status          = document.getElementById('itemStatus').value;
            const currentUser     = localStorage.getItem('userId');
            const formJournal = new URLSearchParams({
                dateFrom: encodeURIComponent(dateFrom),
                dateTo: encodeURIComponent(dateTo),
                area: encodeURIComponent(area),
                userCreator: encodeURIComponent(userCreator),
                status: encodeURIComponent(status),
                category: encodeURIComponent(category),
                equipment: encodeURIComponent(equipment),
                statistics: encodeURIComponent(journalParams),
                userInspector: encodeURIComponent(userInspector),
                currentUser: encodeURIComponent(currentUser),
            });
            table.innerHTML       = '';
            try {
                const url = `/journalRequest?${formJournal.toString()}`;
                let number = 1;
                fetch(url)
                   .then(response => response.json())
                   .then((journalData) => {
                        if (journalData.result){
                            journalData.listJournalData.forEach((item) => {
                                const row = document.createElement('tr');
                                row.classList.add('main-tr');
                                row.innerHTML = `
                                    <td>${number}</td>
                                    <td>${item.dateRequest}</td>
                                    <td>${item.area}</td>
                                    <td>${item.equipment}</td>
                                    <td>${item.normativeCount}</td>
                                    <td>${item.factCount}</td>
                                    <td>${item.spisCount}</td>
                                    <td>${item.planedCount}</td>
                                    <td>${item.status}</td>
                                    <td>${item.userStatus}</td>
                                    <td>${item.dateStatus}</td>
                                `;
                                number++;
                                row.addEventListener('click', () => {
                                    const params = new URLSearchParams({
                                        id: item.id
                                    });
                                    let requestUrl;
                                    if (globalStatus == '1'){
                                        requestUrl = `requestAdd.html?${params.toString()}`;
                                    } else {
                                        requestUrl = `requestCorrect.html?${params.toString()}`;
                                    }
                                    window.open(requestUrl, '_blank');
                                });
                                table.appendChild(row);
                            })
                        }
                   });
            } catch (err) {
                alert("Ошибка в коде ");
            }
        }

        function getStatus(){
             try {
                 fetch('/getCurrentStatus')
                       .then(response => response.json())
                       .then((responseStatus) => {
                        if (responseStatus.result){
                            responseStatus.listStatus.forEach((item) => {
                                globalStatus = item.name;
                            })
                        }
                 });
            } catch (err) {
                alert("Ошибка при заполнении журнала ");
            }
        }

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
                        const option = document.createElement('option');
                        option.value = select.options.length + 1;
                        option.textContent = "";
                        select.appendChild(option);
                        select.value = "";
                   });
             } catch (err) {
               alert("Ошибка при получении районов ");
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
                            const option = document.createElement('option');
                            option.value = select.options.length + 1;
                            option.textContent = "";
                            select.appendChild(option);
                        }
                        select.value = "";
                   });
             } catch (err) {
               alert("Ошибка при заполнении списка пользователей ");
             }
        }

        function fillUsersController(){
            try {
                fetch('/usersController')
                   .then(response => response.json())
                   .then((commonData) => {
                        const select = document.getElementById('itemFioInspector');
                        if (commonData.result){
                            commonData.userDataList.forEach((item) => {
                                const option = document.createElement('option');
                                option.value = item.id;
                                option.textContent = item.name;
                                select.appendChild(option);
                            })
                            const option = document.createElement('option');
                            option.value = select.options.length + 1;
                            option.textContent = "";
                            select.appendChild(option);
                        }
                        select.value = "";
                   });
             } catch (err) {
               alert("Ошибка при заполнении списка пользователей - контролеров ");
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
                                const option = document.createElement('option');
                                option.value = item.id;
                                option.textContent = item.name;
                                select.appendChild(option);
                            })
                        }
                        fillEquipmentL2();
                        const option = document.createElement('option');
                        option.value = select.options.length + 1;
                        option.textContent = "";
                        select.appendChild(option);
                        select.value = "";
                   });
             } catch (err) {
               alert("Ошибка при получении категорий ");
             }
        }

        function fillEquipmentL2(){
           try {
                let equipmentName   = document.getElementById('itemEquipmentL1').value;
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
                        const option = document.createElement('option');
                        option.value = select.options.length + 1;
                        option.textContent = "";
                        select.appendChild(option);
                        select.value = "";
                        fillTable();
                   });
            } catch (err) {
               alert("Ошибка при получении оборудования ");
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
                                option.value = item.id;
                                option.textContent = item.name;
                                select.appendChild(option);
                            })
                        }
                        const option = document.createElement('option');
                        option.value = select.options.length + 1;
                        option.textContent = "";
                        select.appendChild(option);
                        select.value = "";
                   });
             } catch (err) {
               alert("Ошибка при получении статусов ");
             }
        }

        function voidClearFioCreator(){
             const select = document.getElementById('itemFioCreator');
             select.value = "";
             fillTable();
        }

        function voidClearFioInspector(){
             const select = document.getElementById('itemFioInspector');
             select.value = "";
             fillTable();
        }

        function voidClearArea(){
             const select = document.getElementById('itemArea');
             select.value = "";
             fillTable();
        }

        function voidClearStatus(){
             const select = document.getElementById('itemStatus');
             select.value = "";
             fillTable();
        }

         function voidClearEquipmentL1(){
              const select = document.getElementById('itemEquipmentL1');
              select.value = "";
              fillTable();
         }

         function voidClearEquipmentL2(){
              const select = document.getElementById('itemEquipmentL2');
              select.value = "";
              fillTable();
         }

        document.addEventListener("DOMContentLoaded", function() {
             const params = new URLSearchParams(window.location.search) || {};
             if ((params != undefined) && (params.size > 0)) {
                journalParams = params.get('statistics');
             }
             const logo = document.getElementById('itemLogo');
             if (journalParams === "0") logo.textContent = "Журнал регистрации заявок";
             else if (journalParams === "2") logo.textContent = "Журнал отказных заявок";
             else if (journalParams === "3") logo.textContent = "Журнал исполненных заявок";
             else if (journalParams === "4") logo.textContent = "Журнал удаленных заявок";
             getStatus();
             fillArea();
             fillUsersCreator();
             fillUsersController();
             fillStatus();
             const equipmentL1 = document.getElementById('itemEquipmentL1');
             equipmentL1.addEventListener('change', fillEquipmentL2);
             const equipmentL2 = document.getElementById('itemEquipmentL2');
             equipmentL2.addEventListener('change', fillTable);
             const area = document.getElementById('itemArea');
             area.addEventListener('change', fillTable);
             const user = document.getElementById('itemFioCreator');
             user.addEventListener('change', fillTable);
             const userInspector = document.getElementById('itemFioInspector');
             userInspector.addEventListener('change', fillTable);
             const status = document.getElementById('itemStatus');
             status.addEventListener('change', fillTable);
             const dateFrom = document.getElementById('itemDateFrom');
             dateFrom.addEventListener('change', fillTable);
             const dateTo = document.getElementById('itemDateTo');
             dateTo.addEventListener('change', fillTable);
             const clearFioCreator = document.getElementById('itemClearFioCreator');
             clearFioCreator.addEventListener('click', voidClearFioCreator);
             const clearFioInspector = document.getElementById('itemClearFioInspector');
             clearFioInspector.addEventListener('click', voidClearFioInspector);
             const clearArea = document.getElementById('itemClearArea');
             clearArea.addEventListener('click', voidClearArea);
             const clearEquipmentL1 = document.getElementById('itemClearEquipmentL1');
             clearEquipmentL1.addEventListener('click', voidClearEquipmentL1);
             const clearEquipmentL2 = document.getElementById('itemClearEquipmentL2');
             clearEquipmentL2.addEventListener('click', voidClearEquipmentL2);
             const clearStatus = document.getElementById('itemClearStatus');
             clearStatus.addEventListener('click', voidClearStatus);
             fillEquipmentL1();
        });