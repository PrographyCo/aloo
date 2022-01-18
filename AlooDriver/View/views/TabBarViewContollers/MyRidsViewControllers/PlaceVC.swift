//
//  PlaceVC.swift
//  AlooDriver
//
//  Created by macbook on 10/9/21.
//

import UIKit
import GoogleMaps
import Cosmos
class PlaceVC: UIViewController {
    
    @IBOutlet weak var backButton: UIButton!
    @IBOutlet weak var mapView: UIView!
    @IBOutlet var toAddress: UILabel!
    @IBOutlet var fromAddress: UILabel!
    @IBOutlet var date: UILabel!
    @IBOutlet var time: UILabel!
    @IBOutlet var displayTableView: UITableView!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet var serviceType: UILabel!
    @IBOutlet var orderPrice: UILabel!
    @IBOutlet var driverPrice: UILabel!
    @IBOutlet var orderTime: UILabel!
    @IBOutlet var totalDistance: UILabel!
    @IBOutlet weak var ratingView: CosmosView!
    @IBOutlet var orderNo: UILabel!
    
    private var data = [CurrentOrdersItems]()
    public var id:String!
    private let googleMap = GMSMapView()
    private let presnter = PlaceVCPresnter()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presnter.delegate = self
        displayTableView.delegate = self
        displayTableView.dataSource = self
        registerNib()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if L102Language.currentAppleLanguage() == "ar"{
            backButton.transform = .init(rotationAngle: .pi)
        }
        presnter.showOrderDetails(id: id)
    }
    
    @IBAction func backBtn(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
    @IBAction func ReportProblemBtn(_ sender: Any) {
    }
    
    func registerNib(){
        let nib = UINib(nibName: "OrderDetailsTableViewCell", bundle: nil)
        displayTableView.register(nib, forCellReuseIdentifier: OrderDetailsTableViewCell.identifier)
    }
    
    
}


extension PlaceVC:UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: OrderDetailsTableViewCell.identifier, for: indexPath) as! OrderDetailsTableViewCell
        cell.setCurrentOrdersItem(data: data[indexPath.row])
        return cell
    }
    
    
}


extension PlaceVC:PlaceVCPresnterDelegate{
    func showAlert(title: String, message: String) {
        GeneralActions.shard.showAlert(viewController: self, title: title, message: message)
    }
    
    func showOrderResponse(data: ShowCurrentOrders) {
        
        date.text = String(data.date ?? 0)
        toAddress.text = data.place?.name ?? ""
        fromAddress.text = data.place?.location_name ?? ""
        nameLabel.text = data.customer?.name ?? ""
        ratingView.rating = data.customer?.rate ?? 0.0
        serviceType.text = data.order_type?.name ?? ""
        orderPrice.text = String(data.total_price ?? 0.0 )
        driverPrice.text = data.delivery_price ?? ""
        orderTime.text = String(data.delivery_time ?? 0)
        totalDistance.text = data.distance ?? ""
        orderNo.text = String(data.id ?? 0)
        self.data = data.items ?? []
        displayTableView.reloadData()
        showGoogleMap(withCoordinate: .init(latitude: Double(data.place?.lat ?? "0.0") ?? 0.0, longitude: Double(data.place?.lon ?? "0.0") ?? 0.0))
    }
    
    
    private func showGoogleMap(withCoordinate coordinate :CLLocationCoordinate2D) {
        let camera = GMSCameraPosition.camera(withLatitude: coordinate.latitude, longitude: coordinate.longitude, zoom: 16)
        googleMap.frame = mapView.bounds
        googleMap.camera = camera
        mapView.addSubview(googleMap)
        let userMarker = GMSMarker()
        userMarker.position = coordinate
        userMarker.iconView = UIImageView(image: #imageLiteral(resourceName: "current-user-location"))
        userMarker.map = googleMap
    }
    
}
