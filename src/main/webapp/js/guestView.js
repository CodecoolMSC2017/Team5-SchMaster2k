function onLoad(){

    if(document.getElementById("testDivForTable").firstChild){
        document.getElementById("testDivForTable").removeChild(document.getElementById("testDivForTable").firstChild);
    }

    const hm = JSON.parse(this.responseText);
    hashMap=hm;
    const days=["zero","mo","tu","we","th","fr","sa","su"];
    const fullDays=["zero","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"];
    const table = document.createElement("table"); //<table></table>
    table.border="1";
    const trHeader = document.createElement("tr");
    for(let i=0;i<8;i++){
        const tdEl=document.createElement("td");
        if(i!=0){
            tdEl.innerHTML=fullDays[i];
            tdEl.style.backgroundColor = "#4CAF50";
            tdEl.style.color = "white";
            tdEl.style.fontWeight = "bold";
        }
        trHeader.appendChild(tdEl);
    }
    table.appendChild(trHeader);
    for(let r = 0;r<24;r++){
        const tr = document.createElement("tr");

        for(let c = 0;c<8;c++){
            const day=days[c]
            const td = document.createElement("td");
            if(c==0){
                td.innerHTML = r + '-' + ++r;
                --r;
                td.style.backgroundColor = "#4CAF50";
                td.style.color = "white";
                td.style.fontWeight = "bold";
                tr.appendChild(td);
            }else{

                td.id=days[c]+r;
                const addButton = document.createElement("button");
                addButton.innerHTML = "Add task";
                addButton.id=days[c]+r;
                addButton.classList.add("addTaskButton");
                addButton.classList.add("button");
                addButton.classList.add("hidden");

                addButton.addEventListener("click",addTaskToSch);
                td.addEventListener("mouseover",showAddButton);
                td.addEventListener("mouseleave",hideAddButton);
                td.appendChild(addButton);
                tr.appendChild(td);


            }
        }
        table.appendChild(tr);
    }
    const div = document.getElementById("testDivForTable");

        div.appendChild(table);

    for (let i = 0, keys = Object.keys(hm), ii = keys.length; i < ii; i++) {

        const tdAppend = document.getElementById(keys[i]);

              //console.log(keys[i]);
              tdAppend.innerHTML = hm[keys[i]].name;
      }

    for(let k = 1;k<8;k++){
        for(let l = 0;l<23;l++){

            if(document.getElementById(days[k]+String(l)).innerHTML == document.getElementById(days[k]+String(l+1)).innerHTML){
               document.getElementById(days[k]+String(l)).style.backgroundColor="#c6f993";
               document.getElementById(days[k]+String(l+1)).style.backgroundColor="#c6f993";
            }
        }
    }
}

window.onload = onLoad;
