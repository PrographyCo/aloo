<?php
namespace App\DataTables\Admin\Vendor;

use App\Models\Branch;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class BranchDataTable extends DataTable
{
    protected $vendor_id;
    public function __construct($vendor_id)
    {
        $this->vendor_id = $vendor_id;
        Branch::$show = true;
    }

    /**
     * Build DataTable class.
     *
     * @param mixed $query Results from query() method.
     * @return \Yajra\DataTables\DataTableAbstract
     */
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

    /**
     * Get query source of dataTable.
     *
     * @param \App\User $model
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function query(Branch $model)
    {
        return $model
            ->newQuery()->where('vendor_id', $this->vendor_id);
    }

    /**
     * Optional method if you want to use html builder.
     *
     * @return \Yajra\DataTables\Html\Builder
     */
    public function html()
    {
        return $this->builder()
            ->setTableId('branch-table')
            ->columns($this->getColumns())
            ->ajax(route('admin.vendor.datatable', [$this->vendor_id, 'branch']))
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
            Column::make('name')->title(__('web/vendor/datatable.branch.name')),
            Column::make('login_number')->title(__('web/vendor/datatable.branch.username')),
            Column::make('available_status')->title(__('web/vendor/datatable.branch.available_status')),
            Column::make('managerPhone')->title(__('web/vendor/datatable.branch.phone')),
            Column::make('managerEmail')->title(__('web/vendor/datatable.branch.email')),
            Column::make('manager')->title(__('web/vendor/datatable.branch.manager')),
            Column::make('managerPosition')->title(__('web/vendor/datatable.branch.managerPosition')),
            Column::make('created_at')->title(__('web/vendor/datatable.branch.created_at')),
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
        return 'branches_' . date('YmdHis');
    }
}
