import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/service/auth/auth.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NgxNotificationMsgService, NgxNotificationStatusMsg} from 'ngx-notification-msg'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  isSignUpFailed = false;
  errorMessage = '';
  hidePassword = true;

  formRegistrationUser : FormGroup = new FormGroup({
    "id" : new FormControl(null),
    "name" : new FormControl(null, [Validators.required]),
    "surname" : new FormControl(null, [Validators.required]),
    "username" : new FormControl(null, [Validators.required]),
    "email" : new FormControl(null, [Validators.required, Validators.email]),
    "password" : new FormControl(null, [Validators.required])
  });

  constructor(private authService: AuthService, private location: Location, private router: Router, private readonly ngxNotificationMsgService: NgxNotificationMsgService) { }

  ngOnInit(): void {
  }

  saveUser(): void {
    if(this.formRegistrationUser.valid) {
      this.authService.register(this.formRegistrationUser.value).subscribe(
        data => {
          console.log(data);
          this.isSignUpFailed = false;
          this.formRegistrationUser.reset();
          this.router.navigate(['login']);

          this.ngxNotificationMsgService.open({
            status: NgxNotificationStatusMsg.SUCCESS,
            header: 'Registration',
            messages: ['You have successfully registered.']
          });

        },
        err => {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        }
      );
    }
  }

  cancel(){
    this.formRegistrationUser.reset();
    this.location.back();
  }
}
