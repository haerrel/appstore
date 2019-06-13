import { Component, OnInit } from '@angular/core';
import {Problem} from '../../../shared/problem';
import {BackendService} from '../../../services/Backend/backend.service';
import {MyToastrService} from "../../../services/toast/my-toastr.service";

@Component({
  selector: 'app-report-problem',
  templateUrl: './report-problem.component.html',
  styleUrls: ['./report-problem.component.css']
})
export class ReportProblemComponent implements OnInit {

  problem = new Problem();

  constructor(private backend: BackendService, private toastr: MyToastrService) { }

  ngOnInit() {
  }

  submit() {
    this.backend.reportProblem(this.problem).toPromise()
      .then((data) => this.toastr.success(`Meldung wurde gesendet`, 'Problemformular'))
      .catch((err) => this.toastr.error(`Meldung konnte nicht gesendet werden`, 'Problemformular'));
  }
}
