import { Component, OnInit } from '@angular/core';
import {Problem} from '../../../shared/problem';
import {BackendService} from '../../../services/Backend/backend.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-show-problem',
  templateUrl: './show-problem.component.html',
  styleUrls: ['./show-problem.component.css']
})
export class ShowProblemComponent implements OnInit {

  problem = new Problem();

  constructor(private route: ActivatedRoute, private backend: BackendService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const problemId = parseInt(params.get('id'), 10);
      this.backend.getProblem(problemId).subscribe(problem => this.problem = problem);
    });
  }

  delete() {
    this.backend.deleteProblem(this.problem.id).toPromise()
      .then(() => {
        this.toastr.success('Meldung gelöscht', 'Problemmeldung');
        this.router.navigate(['../'], {relativeTo: this.route});
      })
      .catch((err) => {
        this.toastr.error('Meldung konnte nicht gelöscht werden', 'Problemmeldung');
      });

  }
}
