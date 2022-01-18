//
//  MyRidesTableViewCell.swift
//  AlooDriver
//
//  Created by Hany Alkahlout on 10/10/2021.
//

import UIKit

class MyRidesTableViewCell: UITableViewCell {
    
    @IBOutlet weak var arrowImageView: UIImageView!
    @IBOutlet weak var pathLineImageView: UIImageView!
    @IBOutlet weak var cellIconImageView: UIImageView!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var fromLocationLabel: UILabel!
    @IBOutlet weak var toLocationLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        if L102Language.currentAppleLanguage() == "ar"{
            pathLineImageView.transform = .init(rotationAngle: .pi)
            arrowImageView.transform = .init(rotationAngle: .pi)
        }
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    
    func setData(data:CurrentItems){
        if let type = data.vendor_type?.name{
            switch type {
            case "supermarkets":
                cellIconImageView.image = #imageLiteral(resourceName: "cart-icon")
                break
            case "resturants":
                cellIconImageView.image = #imageLiteral(resourceName: "unselected-res-icon")
                break
            case "pharmacys":
                cellIconImageView.image = #imageLiteral(resourceName: "pp-icon")
                break
            default:
                break
            }
        }
        
        timeLabel.text = String(data.date ?? -1)
        nameLabel.text = data.customer_name ?? ""
        fromLocationLabel.text = data.place?.name ?? ""
        toLocationLabel.text = data.place?.location_name ?? ""
        priceLabel.text = "\(data.total ?? "0") \(NSLocalizedString("SR", comment: ""))"
    }
    
    
    
}
