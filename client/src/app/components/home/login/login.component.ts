import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/service/auth/auth.service';
import { TokenStorageService } from 'src/app/service/token-storage/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  hidePassword = true;

  formLogin : FormGroup = new FormGroup({
    "username" : new FormControl(null, [Validators.required]),
    "password" : new FormControl(null, [Validators.required])
  });

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  login(): void {
    this.authService.login(this.formLogin.value).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);
        this.isLoginFailed = false;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.href="home"
  }

}
