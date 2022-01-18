//
//  MyTrips.swift
//  Aloo
//
//  Created by macbook on 10/2/21.
//

import UIKit

class MyTrips: UIViewController {
    
    @IBOutlet weak var tripsTableView: UITableView!
    
    
    private var tripsData = [CurrentItems]()
    private let presnter = MyTripsPresnter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initlization()
    }
    
    private func initlization(){
        presnter.delegate = self
        setUpTableView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        presnter.getCurrentOrders()
    }
    
}


extension MyTrips:UITableViewDelegate,UITableViewDataSource{
    private func setUpTableView(){
        tripsTableView.delegate = self
        tripsTableView.dataSource = self
        tripsTableView.register(.init(nibName: "MyRidesTableViewCell", bundle: nil), forCellReuseIdentifier: "MyRidesTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tripsData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MyRidesTableViewCell", for: indexPath) as! MyRidesTableViewCell
        cell.setData(data: tripsData[indexPath.row])
        return cell
        
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let vc = storyboard?.instantiateViewController(withIdentifier: "PlaceVC") as! PlaceVC
        vc.id = String(tripsData[indexPath.row].id ?? -1)
        navigationController?.pushViewController(vc, animated: true)
    }
    
    
}

extension MyTrips:MyTripsPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func currentOrdersResponse(data: GetCurrentOrdersData) {
        tripsData = data.items ?? []
        tripsTableView.reloadData()
    }
    
}
