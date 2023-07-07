window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector("#sidebarToggle");
  if (sidebarToggle) {
    // Uncomment Below to persist sidebar toggle between refreshes
    // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
    //     document.body.classList.toggle('sb-sidenav-toggled');
    // }
    sidebarToggle.addEventListener("click", (event) => {
      event.preventDefault();
      sidebarToggle.classList.toggle("side_toggled");
      var text = $("#sidebarToggle").hasClass("side_toggled")
        ? "Afficher Menu"
        : "Masquer Menu";
      $("#sidebarToggle").text(text);
      document.body.classList.toggle("sb-sidenav-toggled");
      localStorage.setItem(
        "sb|sidebar-toggle",
        document.body.classList.contains("sb-sidenav-toggled")
      );
    });
  }
});

$(document).ready(function () {
  var table = $("#datatablesSimple").DataTable({
    dom: "Bfrtip",
    buttons: ["csv", "excel", "pdf"],
  });
});

//update list after lab selection
updateList = function (e) {
  let labId = e.value != -1 ? e.value : null;
  const parser = new URL(window.location);
  if (labId) {
    parser.searchParams.set("l_id", labId);
    window.location = parser.href;
  } else {
    parser.searchParams.delete("l_id");
    window.location = parser.href;
  }
};

setPage = function (num) {
  const parser = new URL(window.location);
  if (num) {
    parser.searchParams.set("page", num);
  }
  window.location = parser.href;
};

filterByDate = function () {
  let date1 = $("#input_start_date").val();
  let date2 = $("#input_end_date").val();
  let type = $("#input_date_type").val();

  const parser = new URL(window.location);
  parser.searchParams.set("page", 1);
  if (date1) {
    parser.searchParams.set("start", date1);
  } else {
    parser.searchParams.delete("start");
  }
  if (date2) {
    parser.searchParams.set("end", date2);
  } else {
    parser.searchParams.delete("end");
  }
  if (type) {
    parser.searchParams.set("type", type);
  } else {
    parser.searchParams.delete("type");
  }
  window.location = parser.href;
};

filterByPatient = function () {
  const parser = new URL(window.location);
  parser.searchParams.set("page", 1);
  parser.searchParams.delete("site");
  parser.searchParams.delete("platform");
  let patientCode = $("#searchByPatientCode").val();
  if (!patientCode) {
    parser.searchParams.delete("patient_code");
  }
  parser.searchParams.set("patient_code", patientCode);

  window.location = parser.href;
};

filterByStatus = function () {
	const parser = new URL(window.location);
	parser.searchParams.set("page", 1);
	let status = $("#select_status").val();
	if (!status) {
	  parser.searchParams.delete("status");
	}
	parser.searchParams.set("status", status);
  
	window.location = parser.href;
  };

filterBySite = function () {
  const parser = new URL(window.location);
  parser.searchParams.set("page", 1);
  parser.searchParams.delete("patient_code");
  parser.searchParams.delete("platform");
  let site = $("#select_site").val();
  if (!site) {
    parser.searchParams.delete("site");
  }
  parser.searchParams.set("site", site);

  window.location = parser.href;
};

filterByPlatform = function () {
  const parser = new URL(window.location);
  parser.searchParams.set("page", 1);
  parser.searchParams.delete("patient_code");
  parser.searchParams.delete("site");
  let platform = $("#select_platform").val();
  if (!platform) {
    parser.searchParams.delete("platform");
  }
  parser.searchParams.set("platform", platform);

  window.location = parser.href;
};

exportToCSV = function () {
  const parser = new URL(window.location);
  var newLocation = parser.href.replace('analysis','analysis/csv')
  window.location = newLocation;
};