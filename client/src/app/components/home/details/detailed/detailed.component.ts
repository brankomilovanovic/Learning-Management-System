import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-detailed',
  templateUrl: './detailed.component.html',
  styleUrls: ['./detailed.component.css']
})
export class DetailedComponent implements OnInit {

  niz: any[]=[];
  oldObj: any;
  object: any;
  constructor(private router: Router, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    setTimeout(() => {
      this.object = (JSON.parse(this.route.snapshot.paramMap.get('objDetails')));
      this.niz.push(this.object);
    }, 50);
    // this.object = (JSON.parse(this.route.snapshot.paramMap.get('objDetails')));
  }

  newOb(object: any){
    this.niz.push(object);
    this.object = object
    console.log('STAROOOOOOOOOOOOOOOO', this.niz);
   
  }
  back(){

    this.niz.pop();
    this.object = this.niz[this.niz.length-1];

    if(this.niz.length < 1){
      this.location.back()
    }
    // this.object = this.niz.

    
  }
}
