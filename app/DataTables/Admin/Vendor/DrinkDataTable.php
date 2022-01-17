<?php
namespace App\DataTables\Admin\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Drink;
use App\Models\Item;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class DrinkDataTable extends DataTable
{
    protected $vendor_id;
    public function __construct($vendor_id)
    {
        $this->vendor_id = $vendor_id;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('calories', static function ($query){
                return $query->calories . ' ' . __('web/vendor/datatable.drink.calories');
            })

            ->addColumn('action', static function ($query){
//                todo make plus to show all data about order
                return 'action';
            })->rawColumns([
                'action',
            ]);
    }


    public function query(Drink $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', $this->vendor_id);
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('drink-table')
            ->columns($this->getColumns())
            ->ajax(route('admin.vendor.datatable', [$this->vendor_id, 'drink']))
//            ->dom('Bfrtip')
            ->orderBy(0)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.drink.name')),
            Column::make('price')->title(__('web/vendor/datatable.drink.price')),
            Column::make('calories')->title(__('web/vendor/datatable.drink.calories')),
            Column::make('created_at')->title(__('web/vendor/datatable.item.created_at')),
//            Column::computed('action')
//                ->exportable(false)
//                ->printable(false)
//                ->width(60)
//                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'drinks_' . date('YmdHis');
    }
}
