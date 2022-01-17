<?php
namespace App\DataTables\Admin\Driver;

use App\Http\Controllers\Controller;
use App\Models\Car;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class CarDataTable extends DataTable
{
    protected $driver_id;
    public function __construct($driver_id)
    {
        $this->driver_id = $driver_id;
        Car::$show = true;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->addColumn('action', static function ($query){
//                todo make plus to show all data about order
                return 'action';
            })->rawColumns([
                'action',
                'img',
            ]);
    }


    public function query(Car $model)
    {
        return $model
            ->newQuery()->where('driver_id', $this->driver_id)->with([
                'city:id,name_' . Controller::$lang
            ]);
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('car-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }


    protected function getColumns()
    {
        return [
            Column::make('name')->title(__('web/driver/datatable.car.name')),
            Column::make('login_number')->title(__('web/driver/datatable.car.username')),
            Column::make('phone')->title(__('web/driver/datatable.car.phone')),
            Column::make('email')->title(__('web/driver/datatable.car.email')),
            Column::make('gender')->title(__('web/driver/datatable.car.gender')),
            Column::make('city.name_' . Controller::$lang)->title(__('web/driver/datatable.car.city')),
            Column::make('idNumber')->title(__('web/driver/datatable.car.idNumber')),
            Column::make('created_at')->title(__('web/driver/datatable.car.created_at')),
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
        return 'car_' . date('YmdHis');
    }
}
