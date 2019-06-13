import { Component, OnInit } from '@angular/core';
import {BackendService} from '../../../services/Backend/backend.service';
import {Problem} from '../../../shared/problem';
import {MyToastrService} from "../../../services/toast/my-toastr.service";

@Component({
  selector: 'app-show-all-problems',
  templateUrl: './show-all-problems.component.html',
  styleUrls: ['./show-all-problems.component.css']
})
export class ShowAllProblemsComponent implements OnInit {

  problems: Array<Problem> = [];

  constructor(private backend: BackendService, private toastr: MyToastrService) { }

  ngOnInit() {
    this.backend.getProblems().subscribe(problems => this.problems = problems);
  }

  deleteProblem(id: number, arrIndex: number) {
    this.backend.deleteProblem(id).toPromise()
      .then(() => {
        this.toastr.success('Meldung gelöscht', 'Problemmeldung');
        this.problems.splice(arrIndex, 1);
      })
      .catch((err) => {
        this.toastr.error('Meldung konnte nicht gelöscht werden', 'Problemmeldung');
      });
  }
}
