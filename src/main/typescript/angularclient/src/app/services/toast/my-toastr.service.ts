import {Injectable} from '@angular/core';
import {ActiveToast, IndividualConfig, ToastrService} from "ngx-toastr";

@Injectable({
  providedIn: 'root'
})
export class MyToastrService extends ToastrService {

  success(message?: string, title?: string, override?: Partial<IndividualConfig>): ActiveToast<any> {
    return super.success(message, title, {'positionClass': 'toast-bottom-right'});
  }

  error(message?: string, title?: string, override?: Partial<IndividualConfig>): ActiveToast<any> {
    return super.error(message, title, {'positionClass': 'toast-bottom-right'});
  }
}
