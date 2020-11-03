
//client side routing

window.onload = () => {
    const currentPath = window.location.pathname;
    console.log(currentPath);
    const content = document.getElementById('content');


    switch (currentPath) {
        case '/home':
            {
                content.innerHTML = '<h1> Home Page</h1>';
                break;

            }
        case '/addrecord':
            {
                content.innerHTML = `
                <form id="recordForm">
  <div class="form-group">
    <label for="date">Date</label>
    <input type="date" class="form-control" name="date" aria-describedby="dateHelp">
    <small name="dateHelp" class="form-text text-muted">Please enter the date for this record.</small>
  </div>
  <div class="form-group">
    <label for="buildingFund">Building Fund</label>
    <input type="number" class="form-control" name="buildingFund">
  </div>
  
  <div class="form-group">
    <label for="publicOffering">Public Offering</label>
    <input type="number" class="form-control" name="publicOffering">
  </div>
  <div class="form-group">
    <label for="benevolent">Benevolent</label>
    <input type="number" class="form-control" name="benevolent">
  </div>
  
  <div class="form-group">
  <div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" id="groupDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Group Day
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <button type="button" id="men" class="dropdown-item" onclick=selectGroup(this.id) >Men </button>
    <button type="button" id="youth" class="dropdown-item" onclick=selectGroup(this.id) >Youth </button>
    <button type="button" id="women" class="dropdown-item" onclick=selectGroup(this.id) >Women </button>
    <button type="button" id="kevin" class="dropdown-item" onclick=selectGroup(this.id) >Kevin </button>
  </div>
</div>
    <input type="number" class="form-control" name="buildingFund">
  </div>
  <button type="button" onclick=addRecord() class="btn btn-primary">Add Record</button>
</form>
                `
                ;
                break;

            }
        case '/viewrecords':
            {
                content.innerHTML = '<h1> View Record Page</h1>';
                break;

            }
        case '/news':
            {
                content.innerHTML = '<h1> News Page</h1>';
                break;
            }
        default:
            {
                content.innerHTML = `
                <div class="jumbotron">
  <h1 class="display-4">Page '${currentPath.substring(1)}' not found!</h1>
  <p class="lead">This page cannot be found, please click the below button to return to the home page.</p>
  <hr class="my-4">
  <a class="btn btn-primary btn-lg" href="/home" role="button">Home</a>
</div>
`
            }
    }
};

function selectGroup(id)
{
    console.log(id);
    document.getElementById("groupDropdown").innerHTML= document.getElementById(id).innerHTML;
    
}

async function addRecord()
{
    const formData = $('#recordForm').serializeArray();
    let data={};
    for(e of formData)
    {
        data[e.name]=e.value
    }

    //convert date into date object
    data.date = Date(data.date);
    await db.collection('records').add(data);

    console.log(data);

}